package vn.metech.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.*;
import vn.metech.dto.MbfCallReferenceResult;
import vn.metech.dto.MtCallFilter;
import vn.metech.dto.Result;
import vn.metech.dto.request.MtCallRequest;
import vn.metech.dto.response.MtCallRequestListResponse;
import vn.metech.dto.response.UserResponse;
import vn.metech.entity.CallRequest;
import vn.metech.entity.CallResponse;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CallRequestDuplicateException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.redis.util.RedisUtil;
import vn.metech.repository.ICallRequestRepository;
import vn.metech.repository.ICallResponseRepository;
import vn.metech.repository.jpa.CallRequestCrudRepository;
import vn.metech.service.BalanceService;
import vn.metech.service.ICallRequestService;
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
public class CallRequestServiceImpl extends ServiceImpl<CallRequest> implements ICallRequestService {

	private LocalService localService;
	private BalanceService balanceService;
	private RedisUtil<UserInfo> userRedisUtil;
	private RedisUtil<String> redisUtil;
	private RedisUtil<PartnerInfo> partnerRedisUtil;
	private ICallRequestRepository callRequestRepository;
	private ICallResponseRepository callResponseRepository;
	private CallRequestCrudRepository callRequestCrudRepository;
	private final boolean featureFlag;
	private final int requestTimeoutInHour;
	private final int duplicateInDays;

	public CallRequestServiceImpl(
					LocalService localService,
					BalanceService balanceService,
					RedisUtil<UserInfo> userRedisUtil,
					RedisUtil<PartnerInfo> partnerRedisUtil,
					ICallRequestRepository callRequestRepository,
					ICallResponseRepository callResponseRepository,
					CallRequestCrudRepository callRequestCrudRepository,
					@Value("${flag.feature:false}") boolean featureFlag,
					@Value("${mbf.request.timeout:48}") int requestTimeoutInHour,
					@Value("${mbf.request.duplicate-in-days:7}") int duplicateInDays) {
		super(callRequestRepository);
		this.localService = localService;
		this.balanceService = balanceService;
		this.userRedisUtil = userRedisUtil;
		this.partnerRedisUtil = partnerRedisUtil;
		this.callRequestRepository = callRequestRepository;
		this.callResponseRepository = callResponseRepository;
		this.featureFlag = featureFlag;
		this.requestTimeoutInHour = requestTimeoutInHour;
		this.duplicateInDays = duplicateInDays;
		this.callRequestCrudRepository = callRequestCrudRepository;
	}

	@Override
	public CallRequest receivedCustomerRequest(
					MtCallRequest mtRequest, VerifyService verifyService,
					RequestType requestType, String userId, String remoteAddr)
					throws CustomerCodeNotFoundException, CallRequestDuplicateException, BalanceNotEnoughException {
		validateRequest(mtRequest, verifyService, userId);

		CallRequest callRequest = new CallRequest(mtRequest, userId, remoteAddr);
		callRequest.setVerifyService(verifyService);
		callRequest.setRequestType(requestType);
		CallRequest duplicateRequest = callRequestRepository
						.findDuplicateCallRequestIncludeResponseBy(mtRequest, DateUtils.addDay(new Date(), -duplicateInDays));
		if (duplicateRequest != null) {
			CallResponse callResponse = duplicateRequest.getCallResponse();
			if (callResponse != null && callResponse.getMbfStatus() == MbfStatus.SUCCESS
							&& callResponse.getResponseStatus() == ResponseStatus.ANSWER_RECEIVED) {
				MbfCallReferenceResult callResult =
								JsonUtils.toObject(callResponse.getResponseData(), MbfCallReferenceResult.class);
				if (callResult != null && callResult.getCallStatus() != null
								&& (callResult.getCallStatus().getStatus1() == Result.MATCH
								|| callResult.getCallStatus().getStatus2() == Result.MATCH)) {
					callRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
					callRequest.setCallResponse(callResponse);
					callRequest.setCharged(true);
					callRequest.setDuplicate(true);
					callRequest.setDuplicateWith(duplicateRequest.getId());
				}
			}
		}
		if (callRequest.getCallResponse() == null) {
			CallResponse callResponse = callResponseRepository.create(new CallResponse(callRequest));
			callRequest.setCallResponse(callResponse);
		}
		return callRequestRepository.create(callRequest);
	}

	private void validateRequest(
					MtCallRequest mtRequest, VerifyService verifyService, String userId)
					throws CustomerCodeNotFoundException, CallRequestDuplicateException, BalanceNotEnoughException {
		UserResponse userResponse = localService.getUserInfo(userId);
		PartnerInfo partnerInfo = partnerRedisUtil.getValue(CacheKey.Auth.partnerOf(userId));
		if (userResponse != null && StringUtils.isEmpty(mtRequest.getCustomerCode())) {
			mtRequest.setCustomerCode(userResponse.getDefaultCustomerCode());
		} else if (userResponse == null || StringUtils.isEmpty(userResponse.getDefaultCustomerCode())
						|| !partnerInfo.existCustomerCode(mtRequest.getCustomerCode())) {
			throw new CustomerCodeNotFoundException();
		}
		if (callRequestCrudRepository.countByCustomerRequestId(mtRequest.getRequestId()) > 0) {
			throw new CallRequestDuplicateException();
		}
		if (featureFlag) {
			if (!balanceService.checkBalance(verifyService, userId, mtRequest.getRequestId())) {
				throw new BalanceNotEnoughException();
			}
		}
	}

//	@Override
//	public CallRequest getRequestIncludeResponseBy(String requestId, RequestStatus... requestStatus) {
//		return callRequestRepository.getRequestBy(requestId, requestStatus);
//	}

	@Override
	public CallRequest update(CallRequest callRequest) {
		callRequest.setUpdatedDate(new Date());
		callRequest.getCallResponse().setUpdatedBy(callRequest.getUpdatedBy());
		callRequest.getCallResponse().setUpdatedDate(callRequest.getUpdatedDate());

		return super.update(callRequest);
	}

	@Override
	public PagedResult<MtCallRequestListResponse> fillCallReferenceRequestBy(
					MtCallFilter toFilter, String userId, String remoteAddr) {
		List<String> userIds = localService.getRecordsUserIds(userId);
		if (userIds == null || userIds.isEmpty()) {
			return new PagedResult<>();
		}

		PagedResult<CallRequest> pagedRequests = callRequestRepository.getRequestsBy(toFilter, userIds);

		return new PagedResult<>(
						pagedRequests.getTotalRecords(),
						MtCallRequestListResponse.fromList(pagedRequests.getData())
		);
	}

	@Override
	public List<CallRequest> getSyncibleTpcRequests() {
		List<String> tpcUserIds = localService.getTpcUserIds();
		if (tpcUserIds == null || tpcUserIds.isEmpty()) {
			return Collections.emptyList();
		}

		final int numberOfRecords = 32;

		return callRequestRepository.getGroupSyncibleRequestsIncludeResponseBy(numberOfRecords, tpcUserIds);
	}

}
