package vn.metech.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.*;
import vn.metech.dto.MbfLocationResult;
import vn.metech.dto.request.MtLocationFilterRequest;
import vn.metech.dto.request.MtLocationRequest;
import vn.metech.dto.response.MtLocationRequestListResponse;
import vn.metech.dto.response.UserResponse;
import vn.metech.entity.LocationRequest;
import vn.metech.entity.LocationResponse;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.exception.LocationRequestDuplicateException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.redis.util.RedisUtil;
import vn.metech.repository.ILocationRequestRepository;
import vn.metech.repository.ILocationResponseRepository;
import vn.metech.repository.jpa.LocationRequestCrudRepository;
import vn.metech.service.BalanceService;
import vn.metech.service.ILocationRequestService;
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
public class LocationRequestServiceImpl extends ServiceImpl<LocationRequest> implements ILocationRequestService {

	private LocalService localService;
	private BalanceService balanceService;
	private RedisUtil<UserInfo> userRedisUtil;
	private RedisUtil<PartnerInfo> partnerRedisUtil;
	private ILocationRequestRepository locationRequestRepository;
	private ILocationResponseRepository locationResponseRepository;
	private LocationRequestCrudRepository locationRequestCrudRepository;
	private final boolean featureFlag;
	private final int requestTimeoutInHour;
	private final int duplicateInDays;

	public LocationRequestServiceImpl(
					LocalService localService,
					BalanceService balanceService,
					RedisUtil<UserInfo> userRedisUtil,
					RedisUtil<PartnerInfo> partnerRedisUtil,
					ILocationRequestRepository locationRequestRepository,
					ILocationResponseRepository locationResponseRepository,
					LocationRequestCrudRepository locationRequestCrudRepository,
					@Value("${flag.feature:false}") boolean featureFlag,
					@Value("${mbf.request.timeout:48}") int requestTimeoutInHour,
					@Value("${mbf.request.duplicate-in-days:7}") int duplicateInDays) {
		super(locationRequestRepository);
		this.userRedisUtil = userRedisUtil;
		this.localService = localService;
		this.balanceService = balanceService;
		this.partnerRedisUtil = partnerRedisUtil;
		this.locationRequestRepository = locationRequestRepository;
		this.locationResponseRepository = locationResponseRepository;
		this.featureFlag = featureFlag;
		this.requestTimeoutInHour = requestTimeoutInHour;
		this.duplicateInDays = duplicateInDays;
		this.locationRequestCrudRepository = locationRequestCrudRepository;
	}

	@Override
	public LocationRequest receivedCustomerRequest(
					MtLocationRequest mtRequest, RequestType requestType, String userId, String remoteAddr)
					throws CustomerCodeNotFoundException, LocationRequestDuplicateException, BalanceNotEnoughException {
		VerifyService verifyService =
						requestType == RequestType.TPC_LOCATION_ADVANCE_VERIFY ? VerifyService.FC03_01 : VerifyService.KYC03;
		validateRequest(mtRequest, verifyService, userId);
		LocationRequest locationRequest = new LocationRequest(mtRequest, userId, remoteAddr);
		locationRequest.setAppService(AppService.LOCATION);
		locationRequest.setVerifyService(verifyService);
		locationRequest.setRequestType(requestType);
		LocationRequest duplicateRequest = locationRequestRepository
						.findDuplicateLocationRequestIncludeResponseBy(mtRequest, DateUtils.addDay(new Date(), -duplicateInDays));
		if (duplicateRequest != null) {
			LocationResponse locationResponse = duplicateRequest.getLocationResponse();
			if (locationResponse != null && locationResponse.getMbfStatus() == MbfStatus.SUCCESS) {
				MbfLocationResult locationResult =
								JsonUtils.toObject(locationResponse.getResponseData(), MbfLocationResult.class);
				if (locationResult != null) {
					locationRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
					locationRequest.setLocationResponse(locationResponse);
					locationRequest.setCharged(true);
					locationRequest.setDuplicate(true);
					locationRequest.setDuplicateWith(duplicateRequest.getId());
				}
			}
		}
		if (locationRequest.getLocationResponse() == null) {
			LocationResponse locationResponse = locationResponseRepository.create(new LocationResponse(locationRequest));
			locationRequest.setLocationResponse(locationResponse);
		}
		return locationRequestRepository.update(locationRequest);
	}

	private void validateRequest(
					MtLocationRequest mtRequest, VerifyService verifyService, String userId)
					throws CustomerCodeNotFoundException, LocationRequestDuplicateException, BalanceNotEnoughException {
		UserResponse userResponse = localService.getUserInfo(userId);
		PartnerInfo partnerInfo = partnerRedisUtil.getValue(CacheKey.Auth.partnerOf(userId));
		if (userResponse != null && StringUtils.isEmpty(mtRequest.getCustomerCode())) {
			mtRequest.setCustomerCode(userResponse.getDefaultCustomerCode());
		} else if (userResponse == null || StringUtils.isEmpty(userResponse.getDefaultCustomerCode())
						|| !partnerInfo.existCustomerCode(mtRequest.getCustomerCode())) {
			throw new CustomerCodeNotFoundException();
		}
		if (locationRequestCrudRepository.countByCustomerRequestId(mtRequest.getRequestId()) > 0) {
			throw new LocationRequestDuplicateException();
		}
		if (featureFlag) {
			if (!balanceService.checkBalance(verifyService, userId, mtRequest.getRequestId())) {
				throw new BalanceNotEnoughException();
			}
		}
	}


	@Override
	public LocationRequest update(LocationRequest locationRequest) {
		locationRequest.setUpdatedDate(new Date());
		locationRequest.getLocationResponse().setUpdatedBy(locationRequest.getUpdatedBy());
		locationRequest.getLocationResponse().setUpdatedDate(locationRequest.getUpdatedDate());

		return super.update(locationRequest);
	}

	@Override
	public PagedResult<MtLocationRequestListResponse> fillLocationRequestBy(
					MtLocationFilterRequest locationFilter, RequestType requestType, String userId, String remoteAddr) {
		List<String> userIds = localService.getRecordsUserIds(userId);
		if (userIds == null || userIds.isEmpty()) {
			return new PagedResult<>();
		}
		PagedResult<LocationRequest> pagedRequests =
						locationRequestRepository.getRequestsBy(locationFilter, requestType, userIds);

		return new PagedResult<>(
						pagedRequests.getTotalRecords(),
						MtLocationRequestListResponse.fromList(pagedRequests.getData())
		);
	}

}
