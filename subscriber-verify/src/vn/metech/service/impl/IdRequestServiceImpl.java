package vn.metech.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.*;
import vn.metech.dto.MbfIdReferenceResult;
import vn.metech.dto.MtIdFilter;
import vn.metech.dto.Result;
import vn.metech.dto.request.MtIdRequest;
import vn.metech.dto.response.MtIdRequestListResponse;
import vn.metech.dto.response.UserResponse;
import vn.metech.entity.IdRequest;
import vn.metech.entity.IdResponse;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.exception.IdRequestDuplicateException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.redis.util.RedisUtil;
import vn.metech.repository.IIdRequestRepository;
import vn.metech.repository.IIdResponseRepository;
import vn.metech.repository.jpa.IdRequestCrudRepository;
import vn.metech.service.BalanceService;
import vn.metech.service.IIdRequestService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;
import vn.metech.shared.PartnerInfo;
import vn.metech.shared.UserInfo;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class IdRequestServiceImpl extends ServiceImpl<IdRequest> implements IIdRequestService {

    private LocalService localService;
    private BalanceService balanceService;
    private RedisUtil<UserInfo> userRedisUtil;
    private RedisUtil<PartnerInfo> partnerRedisUtil;
    private IIdRequestRepository idRequestRepository;
    private IIdResponseRepository idResponseRepository;
    private final boolean featureFlag;
    private final int requestTimeoutInHour;
    private final int duplicateInDays;
    private IdRequestCrudRepository idRequestCrudRepository;

    public IdRequestServiceImpl(
            LocalService localService,
            BalanceService balanceService,
            RedisUtil<UserInfo> userRedisUtil,
            RedisUtil<PartnerInfo> partnerRedisUtil,
            IIdRequestRepository idRequestRepository,
            IIdResponseRepository idResponseRepository,
            IdRequestCrudRepository idRequestCrudRepository,
            @Value("${flag.feature:false}") boolean featureFlag,
            @Value("${mbf.request.timeout:48}") int requestTimeoutInHour,
            @Value("${mbf.request.duplicate-in-days:7}") int duplicateInDays) {
        super(idRequestRepository);
        this.localService = localService;
        this.userRedisUtil = userRedisUtil;
        this.balanceService = balanceService;
        this.partnerRedisUtil = partnerRedisUtil;
        this.idRequestRepository = idRequestRepository;
        this.idResponseRepository = idResponseRepository;
        this.featureFlag = featureFlag;
        this.requestTimeoutInHour = requestTimeoutInHour;
        this.duplicateInDays = duplicateInDays;
        this.idRequestCrudRepository = idRequestCrudRepository;
    }

    private void validateRequest(
            MtIdRequest mtRequest, VerifyService verifyService, String userId)
            throws CustomerCodeNotFoundException, IdRequestDuplicateException, BalanceNotEnoughException {
        UserResponse userResponse = localService.getUserInfo(userId);
        PartnerInfo partnerInfo = partnerRedisUtil.getValue(CacheKey.Auth.partnerOf(userId));
        if (userResponse != null && StringUtils.isEmpty(mtRequest.getCustomerCode())) {
            mtRequest.setCustomerCode(userResponse.getDefaultCustomerCode());
        } else if (userResponse == null || StringUtils.isEmpty(userResponse.getDefaultCustomerCode())
                || !partnerInfo.existCustomerCode(mtRequest.getCustomerCode())) {
            throw new CustomerCodeNotFoundException();
        }
        if (idRequestCrudRepository.countByCustomerRequestId(mtRequest.getRequestId()) > 0) {
            throw new IdRequestDuplicateException();
        }
        if (featureFlag) {
            if (!balanceService.checkBalance(verifyService, userId, mtRequest.getRequestId())) {
                throw new BalanceNotEnoughException();
            }
        }
    }

    @Override
    public PagedResult<MtIdRequestListResponse> getRequestsFrom(MtIdFilter mtIdFilter, String userId) {
        List<String> userIds = localService.getRecordsUserIds(userId);
        if (userIds == null || userIds.isEmpty()) {
            return new PagedResult<>();
        }

        PagedResult<IdRequest> idRequests = idRequestRepository.getRequestsBy(mtIdFilter, userIds);

        return new PagedResult<>(
                idRequests.getTotalRecords(),
                MtIdRequestListResponse.fromList(idRequests.getData())
        );
    }

    @Override
    public IdRequest receivedCustomerRequest(
            MtIdRequest mtRequest, VerifyService verifyService, String userId, String remoteAddr)
            throws IdRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {
        validateRequest(mtRequest, verifyService, userId);
        IdRequest idRequest = new IdRequest(mtRequest, userId, remoteAddr);
        idRequest.setVerifyService(verifyService);
        IdRequest duplicateRequest = idRequestRepository
                .findDuplicateIdRequestIncludeResponseBy(mtRequest, DateUtils.addDay(new Date(), -duplicateInDays));
        if (duplicateRequest != null) {
            IdResponse idResponse = duplicateRequest.getIdResponse();
            if (idResponse != null && idResponse.getMbfStatus() == MbfStatus.SUCCESS) {
                MbfIdReferenceResult idResult = JsonUtils.toObject(idResponse.getResponseData(), MbfIdReferenceResult.class);
                if (idResult != null && idResult.getCurrentIdNumber() != null &&
                        (idResult.getCurrentIdNumber().getResult() == Result.MATCH ||
                                idResult.getCurrentIdNumber().getResult() == Result.NOT_MATCH)) {
                    idRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
                    idRequest.setIdResponse(idResponse);
                    idRequest.setCharged(true);
                    idRequest.setDuplicate(true);
                    idRequest.setDuplicateWith(duplicateRequest.getId());
                }
            }
        }
        if (idRequest.getIdResponse() == null) {
            IdResponse idResponse = idResponseRepository.create(new IdResponse(idRequest));
            idRequest.setIdResponse(idResponse);
        }
        return idRequestRepository.update(idRequest);
    }

//    @Override
//    public IdRequest getRequestIncludeResponseBy(String requestId, RequestStatus... requestStatus) {
//
//        return idRequestRepository.getRequestBy(requestId, requestStatus);
//    }

    @Override
    public IdRequest update(IdRequest idRequest) {
        idRequest.setUpdatedDate(new Date());
        idRequest.getIdResponse().setUpdatedBy(idRequest.getUpdatedBy());
        idRequest.getIdResponse().setUpdatedDate(idRequest.getUpdatedDate());

        return super.update(idRequest);
    }

}
