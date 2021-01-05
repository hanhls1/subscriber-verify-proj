package vn.metech.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.*;
import vn.metech.dto.MbfAdReferenceResult;
import vn.metech.dto.request.MtAdFilterRequest;
import vn.metech.dto.request.MtAdRequest;
import vn.metech.dto.response.AdRequestListResponse;
import vn.metech.dto.response.UserResponseAd;
import vn.metech.entity.AdRequest;
import vn.metech.entity.AdResponse;
import vn.metech.exception.AdRequestDuplicateException;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.redis.util.RedisUtil;
import vn.metech.repository.IAdRequestRepository;
import vn.metech.repository.IAdResponseRepository;
import vn.metech.repository.jpa.AdRequestCrudRepository;
import vn.metech.service.BalanceService;
import vn.metech.service.IAdRequestService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;
import vn.metech.shared.PartnerInfo;
import vn.metech.shared.UserInfo;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AdRequestServiceImpl extends ServiceImpl<AdRequest> implements IAdRequestService {

    private LocalService localService;
    private BalanceService balanceService;
    private RedisUtil<UserInfo> userRedisUtil;
    private RedisUtil<PartnerInfo> partnerRedisUtil;
    private IAdRequestRepository adRequestRepository;
    private IAdResponseRepository adResponseRepository;
    private AdRequestCrudRepository adRequestCrudRepository;
    private final boolean featureFlag;
    private final int requestTimeoutInHour;
    private final int duplicateInDays;

    public AdRequestServiceImpl(
            LocalService localService,
            BalanceService balanceService,
            RedisUtil<UserInfo> userRedisUtil,
            RedisUtil<PartnerInfo> partnerRedisUtil,
            IAdRequestRepository adRequestRepository,
            IAdResponseRepository adResponseRepository,
            AdRequestCrudRepository adRequestCrudRepository,
            @Value("${flag.feature:false}") boolean featureFlag,
            @Value("${mbf.request.timeout:48}") int requestTimeoutInHour,
            @Value("${mbf.request.duplicate-in-days:7}") int duplicateInDays) {
        super(adRequestRepository);
        this.localService = localService;
        this.userRedisUtil = userRedisUtil;
        this.balanceService = balanceService;
        this.partnerRedisUtil = partnerRedisUtil;
        this.adRequestRepository = adRequestRepository;
        this.adResponseRepository = adResponseRepository;
        this.featureFlag = featureFlag;
        this.requestTimeoutInHour = requestTimeoutInHour;
        this.duplicateInDays = duplicateInDays;
        this.adRequestCrudRepository = adRequestCrudRepository;
    }

    @Override
    public AdRequest getRequestById(String requestId) {
        AdRequest adRequest = adRequestRepository.getById(requestId);
        if (adRequest == null || adRequest.isDeleted()) {
            return null;
        }

        return adRequest;
    }

    @Override
    public AdRequest receivedCustomerRequest(
            MtAdRequest mtRequest, VerifyService verifyService, String userId, String remoteAddr)
            throws AdRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {
        validateRequest(mtRequest, verifyService, userId);
        AdRequest adRequest = new AdRequest(mtRequest, userId, remoteAddr);
        adRequest.setVerifyService(verifyService);
        AdRequest duplicateRequest = adRequestRepository
                .findDuplicateAdRequestIncludeResponseBy(mtRequest, DateUtils.addDay(new Date(), -duplicateInDays));
        if (duplicateRequest != null) {
            AdResponse adResponse = duplicateRequest.getAdResponse();
            if (adResponse != null && adResponse.getMbfStatus() == MbfStatus.SUCCESS
                    && adResponse.getResponseStatus() == ResponseStatus.ANSWER_RECEIVED) {
                MbfAdReferenceResult Result = JsonUtils.toObject(adResponse.getResponseData(), MbfAdReferenceResult.class);
                if (Result != null && Result.getCurrentImeiResult() != null &&
                        (Result.getCurrentImeiResult().getResult() == vn.metech.dto.Result.MATCH ||
                                Result.getCurrentImeiResult().getResult() == vn.metech.dto.Result.NOT_MATCH)) {
                    adRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
                    adRequest.setAdResponse(adResponse);
                    adRequest.setCharged(true);
                    adRequest.setDuplicate(true);
                    adRequest.setDuplicateWith(duplicateRequest.getId());
                }
            }
        }
        if (adRequest.getAdResponse() == null) {
            AdResponse adResponse = adResponseRepository.create(new AdResponse(adRequest));
            adRequest.setAdResponse(adResponse);
        }
        return adRequestRepository.update(adRequest);
    }

    private void validateRequest(
            MtAdRequest mtRequest, VerifyService verifyService, String userId)
            throws CustomerCodeNotFoundException, AdRequestDuplicateException, BalanceNotEnoughException {
        UserResponseAd userResponseAd = localService.getUserInfoAd(userId);
        PartnerInfo partnerInfo = partnerRedisUtil.getValue(CacheKey.Auth.partnerOf(userId));
        if (userResponseAd != null && StringUtils.isEmpty(mtRequest.getCustomerCode())) {

            mtRequest.setCustomerCode(userResponseAd.getDefaultCustomerCode());
            //created
            mtRequest.setPartnerId(userResponseAd.getPartnerId());
            mtRequest.setSubPartnerName(userResponseAd.getSubParnerName());
        } else if (userResponseAd == null || StringUtils.isEmpty(userResponseAd.getDefaultCustomerCode())
                || !partnerInfo.existCustomerCode(mtRequest.getCustomerCode())) {
            throw new CustomerCodeNotFoundException();
        }
        if (adRequestCrudRepository.countByCustomerRequestId(mtRequest.getRequestId()) > 0) {
            throw new AdRequestDuplicateException();
        }
        if (featureFlag) {
            if (!balanceService.checkBalance(verifyService, userId, mtRequest.getRequestId())) {
                throw new BalanceNotEnoughException();
            }
        }
    }


    @Override
    public AdRequest getRequestIncludeResponseBy(String requestAd, RequestStatus... requestStatus) {
        return adRequestRepository.getRequestBy(requestAd, requestStatus);
    }

    @Override
    public PagedResult<AdRequestListResponse> getRequestsFrom(MtAdFilterRequest mtAdFilterRequest, String userId) {
        List<String> userIds = localService.getRecordsUserIds(userId);
        if (userIds == null || userIds.isEmpty()) {
            return new PagedResult<>();
        }

        PagedResult<AdRequest> pagedRequests =
                adRequestRepository.getRequestsBy(mtAdFilterRequest.toFilter(), userIds);
        return new PagedResult<>(
                pagedRequests.getTotalRecords(),
                AdRequestListResponse.fromCollection(pagedRequests.getData())
        );
    }

    @Override
    public List<AdRequest> getSyncibleTpcRequests() {
        List<String> tpcUserIds = localService.getTpcUserIds();
        if (tpcUserIds == null || tpcUserIds.isEmpty()) {
            return Collections.emptyList();
        }
        final int numberOfRecords = 32;

        return adRequestRepository.getGroupSyncibleRequestsIncludeResponseBy(numberOfRecords, tpcUserIds);
    }

}
