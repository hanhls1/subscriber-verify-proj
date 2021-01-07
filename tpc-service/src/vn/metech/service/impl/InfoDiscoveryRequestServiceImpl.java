package vn.metech.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.metech.constant.*;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.dto.request.MtInfoDiscoveryRequest;
import vn.metech.dto.response.MtInfoDiscoveryRequestListResponse;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.InfoDiscoveryRequestDuplicateException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.redis.util.RedisUtil;
import vn.metech.repository.IInfoDiscoveryRequestRepository;
import vn.metech.repository.IInfoDiscoveryResponseRepository;
import vn.metech.service.BalanceService;
import vn.metech.service.IInfoDiscoveryRequestService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;
import vn.metech.shared.PartnerInfo;
import vn.metech.shared.UserInfo;
import vn.metech.util.DateUtils;

import java.util.Date;
import java.util.List;

import static vn.metech.constant.RequestStatus.*;

@Service
@Transactional
public class InfoDiscoveryRequestServiceImpl
        extends ServiceImpl<InfoDiscoveryRequest> implements IInfoDiscoveryRequestService {

    private LocalService localService;
    private BalanceService balanceService;
    private RedisUtil<UserInfo> userRedisUtil;
    private RedisUtil<PartnerInfo> partnerRedisUtil;
    private IInfoDiscoveryRequestRepository infoDiscoveryRequestRepository;
    private IInfoDiscoveryResponseRepository infoDiscoveryResponseRepository;
    private boolean featureFlag;

    public InfoDiscoveryRequestServiceImpl(
            LocalService localService,
            BalanceService balanceService,
            RedisUtil<UserInfo> userRedisUtil,
            RedisUtil<PartnerInfo> partnerRedisUtil,
            IInfoDiscoveryRequestRepository infoDiscoveryRequestRepository,
            IInfoDiscoveryResponseRepository infoDiscoveryResponseRepository,
            @Value("${flag.feature:false}") boolean featureFlag) {
        super(infoDiscoveryRequestRepository);
        this.localService = localService;
        this.userRedisUtil = userRedisUtil;
        this.balanceService = balanceService;
        this.partnerRedisUtil = partnerRedisUtil;
        this.infoDiscoveryRequestRepository = infoDiscoveryRequestRepository;
        this.infoDiscoveryResponseRepository = infoDiscoveryResponseRepository;
        this.featureFlag = featureFlag;
    }

    @Override
    public InfoDiscoveryRequest receivedCustomerRequest(
            MtInfoDiscoveryRequest mtRequest, RequestType requestType, String userId, String remoteAddr)
            throws InfoDiscoveryRequestDuplicateException, BalanceNotEnoughException {
        UserInfo userInfo = userRedisUtil.getValue(Auth.Jwt.CACHE_USER_KEY + userId);
        PartnerInfo partnerInfo = partnerRedisUtil.getValue(CacheKey.Auth.partnerOf(userId));
        if (infoDiscoveryRequestRepository.countRequestsBy(mtRequest.getRequestId()) > 0) {
            throw new InfoDiscoveryRequestDuplicateException();
        }
        if (featureFlag) {
            if (!balanceService.checkBalance(VerifyService.KYC02, userId, mtRequest.getRequestId())) {
                throw new BalanceNotEnoughException();
            }
        }
        InfoDiscoveryRequest infoDiscoveryRequest = new InfoDiscoveryRequest(mtRequest, userId, remoteAddr);
        infoDiscoveryRequest.setRequestType(requestType);
        infoDiscoveryRequest.setAppService(AppService.TPC);
        infoDiscoveryRequest.setVerifyService(
                requestType == RequestType.TPC_REQUEST ? VerifyService.KYC02 : VerifyService.KYC02);
        InfoDiscoveryRequest existing = infoDiscoveryRequestRepository.getRequestIncludeResponseBy(mtRequest);
        if (existing != null) {
            infoDiscoveryRequest.setDuplicate(true);
            infoDiscoveryRequest.setRequestStatus(
                    existing.getInfoDiscoveryResponse().getDwhStatus() == DwhStatus.SUCCESS
                            ? ANSWER_RECEIVED
                            : PENDING
            );
            infoDiscoveryRequest.setDuplicateWith(existing.getId());
            infoDiscoveryRequest.setInfoDiscoveryResponse(existing.getInfoDiscoveryResponse());
        }
        if (infoDiscoveryRequest.getInfoDiscoveryResponse() == null) {
            InfoDiscoveryResponse infoDiscoveryResponse = new InfoDiscoveryResponse(infoDiscoveryRequest);
            infoDiscoveryResponseRepository.create(infoDiscoveryResponse);
            infoDiscoveryRequest.setInfoDiscoveryResponse(infoDiscoveryResponse);
        }

        return infoDiscoveryRequestRepository.create(infoDiscoveryRequest);
    }

    @Override
    public List<InfoDiscoveryRequest> getPendingRequestsIncludeResponseWithLimit() {
        final int numberOfRecords = 32;
        final int timeSpaced = 5;
        final int fetchLimit = 3;

        Date fetchAfter = DateUtils.addMinute(new Date(), timeSpaced * -1);

        return infoDiscoveryRequestRepository.getRequestsIncludeResponseBy(
                numberOfRecords, fetchAfter, fetchLimit, true, PENDING
        );
    }

    @Override
    public List<InfoDiscoveryRequest> getAnswerReceivedRequestsIncludeResponseWithLimit() {
        final int numberOfRecords = 32;
        final int timeSpaced = 5;
        final int fetchLimit = 3;

        Date fetchAfter = DateUtils.addMinute(new Date(), timeSpaced * -1);

        return infoDiscoveryRequestRepository.getRequestsIncludeResponseBy(
                numberOfRecords, fetchAfter, fetchLimit, false, ANSWER_RECEIVED, TIMEOUT
        );
    }

    @Override
    public InfoDiscoveryRequest getRequestIncludeResponseBy(
            String requestId, RequestStatus requestStatus) {

        return infoDiscoveryRequestRepository.getRequestBy(requestId, requestStatus);
    }

    @Override
    public InfoDiscoveryRequest update(InfoDiscoveryRequest infoDiscoveryRequest) {
        infoDiscoveryRequest.setUpdatedDate(new Date());
        infoDiscoveryRequest.getInfoDiscoveryResponse().setUpdatedBy(infoDiscoveryRequest.getUpdatedBy());
        infoDiscoveryRequest.getInfoDiscoveryResponse().setUpdatedDate(infoDiscoveryRequest.getUpdatedDate());

        return super.update(infoDiscoveryRequest);
    }

    @Override
    public List<InfoDiscoveryRequest> getTimeoutRequestsIncludeResponseWithLimit() {
        final int numberOfRecords = 32;
        final int timeoutAfter = 6; // hour

        Date createdBefore = DateUtils.addHour(new Date(), timeoutAfter * -1);

        return infoDiscoveryRequestRepository.getRequestsIncludeResponseBy(
                numberOfRecords,
                createdBefore,
                QUESTION_SENT
        );
    }

    @Override
    public List<InfoDiscoveryRequest> getChargeableRequestsIncludeResponseWithLimit() {
        final int numberOfRecords = 32;
        final int timeoutAfter = 6; // hour
        final boolean charged = false;

        Date fetchBefore = DateUtils.addHour(new Date(), timeoutAfter * -1);

        return infoDiscoveryRequestRepository.getRequestsIncludeResponseBy(
                numberOfRecords, fetchBefore, charged, ANSWER_RECEIVED, TIMEOUT, ANSWER_SENT);
    }

    @Override
    public InfoDiscoveryRequest getQuestionSentRequestIncludeResponseBy(String requestId) {
        return infoDiscoveryRequestRepository.getRequestIncludeResponseBy(requestId, QUESTION_SENT);
    }

    @Override
    public PagedResult<MtInfoDiscoveryRequestListResponse> filtersRequestsBy(
            MtInfoDiscoveryFilter infoDiscoveryFilter, RequestType requestType, String userId) {
        List<String> userIds = localService.getRefUserIds(userId);
        if (userIds == null || userIds.isEmpty()) {
            return new PagedResult<>();
        }
        PagedResult<InfoDiscoveryRequest> infoDiscoveryRequests =
                infoDiscoveryRequestRepository.getRequestsBy(infoDiscoveryFilter, requestType, userIds);
        return new PagedResult<>(
                infoDiscoveryRequests.getTotalRecords(),
                MtInfoDiscoveryRequestListResponse.fromCollection(infoDiscoveryRequests.getData())
        );
    }
}
