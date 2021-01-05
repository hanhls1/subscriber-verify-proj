package vn.metech.service;

import vn.metech.constant.VerifyService;
import vn.metech.dto.MtIdFilter;
import vn.metech.dto.request.MtIdRequest;
import vn.metech.dto.response.MtIdRequestListResponse;
import vn.metech.entity.IdRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.exception.IdRequestDuplicateException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface IIdRequestService extends IService<IdRequest> {

	PagedResult<MtIdRequestListResponse> getRequestsFrom(MtIdFilter mtIdFilter, String userId);

	IdRequest receivedCustomerRequest(
					MtIdRequest mtIdRequest, VerifyService verifyService, String userId, String remoteAddr)
					throws IdRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException;

//	IdRequest getRequestIncludeResponseBy(String requestId, RequestStatus... requestStatus);

//	void updateMbfStatus(String id, MbfStatus mbfStatus);
//
//	void updateSubscriberStatus(String id, SubscriberStatus subscriberStatus);
}
