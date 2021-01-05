package vn.metech.service;

import vn.metech.constant.RequestType;
import vn.metech.dto.request.MtLocationFilterRequest;
import vn.metech.dto.request.MtLocationRequest;
import vn.metech.dto.response.MtLocationRequestListResponse;
import vn.metech.entity.LocationRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.exception.LocationRequestDuplicateException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface ILocationRequestService extends IService<LocationRequest> {

	LocationRequest receivedCustomerRequest(
					MtLocationRequest locationRequest,
					RequestType requestType, String userId, String remoteAddr)
					throws CustomerCodeNotFoundException, LocationRequestDuplicateException, BalanceNotEnoughException;

	PagedResult<MtLocationRequestListResponse> fillLocationRequestBy(
					MtLocationFilterRequest locationFilter, RequestType requestType, String userId, String remoteAddr);

}
