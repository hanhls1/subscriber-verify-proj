package vn.metech.service;

import vn.metech.constant.*;
import vn.metech.dto.MtCallFilter;
import vn.metech.dto.request.MtCallRequest;
import vn.metech.dto.response.MtCallRequestListResponse;
import vn.metech.entity.CallRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CallRequestDuplicateException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface ICallRequestService extends IService<CallRequest> {

	CallRequest receivedCustomerRequest(
					MtCallRequest callRequest, VerifyService verifyService,
					RequestType requestType, String userId, String remoteAddr)
					throws CustomerCodeNotFoundException, CallRequestDuplicateException, BalanceNotEnoughException;

//	CallRequest getRequestIncludeResponseBy(String requestId, RequestStatus... requestStatus);

	PagedResult<MtCallRequestListResponse> fillCallReferenceRequestBy(
					MtCallFilter toFilter, String userId, String remoteAddr);

	List<CallRequest> getSyncibleTpcRequests();

//	void updateSubscriberStatus(String requestId, SubscriberStatus subscriberStatus);
//
//	void updateMbfStatus(String requestId, MbfStatus mbfStatus);

}
