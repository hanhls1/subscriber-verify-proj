package vn.metech.service;

import vn.metech.constant.RequestStatus;
import vn.metech.constant.VerifyService;
import vn.metech.dto.request.MtAdFilterRequest;
import vn.metech.dto.request.MtAdRequest;
import vn.metech.dto.response.AdRequestListResponse;
import vn.metech.entity.AdRequest;
import vn.metech.exception.AdRequestDuplicateException;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface IAdRequestService extends IService<AdRequest> {

	AdRequest getRequestById(String requestId);

	AdRequest receivedCustomerRequest(
			MtAdRequest mtAdRequest, VerifyService verifyService, String userId, String remoteAddr)
			throws AdRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException;

	AdRequest getRequestIncludeResponseBy(
			String requestAd, RequestStatus... requestStatus);

	PagedResult<AdRequestListResponse> getRequestsFrom(
			MtAdFilterRequest mtAdFilterRequest, String userId);

	List<AdRequest> getSyncibleTpcRequests();

//	void updateSubscriberStatus(String requestId, SubscriberStatus subscriberStatus);
//
//	void updateMbfStatus(String requestId, MbfStatus mbfStatus);
}
