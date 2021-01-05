package vn.metech.service;

import vn.metech.dto.MtCallFilter;
import vn.metech.dto.request.MtCallFilterRequest;

import vn.metech.dto.response.MtCallReferenceListResponse;
import vn.metech.dto.response.MtCallResponse;
import vn.metech.entity.CallResponse;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface ICallResponseService extends IService<CallResponse> {

	PagedResult<MtCallResponse> getMtCallsVerifyResponseFrom(
					MtCallFilterRequest mtCallRequest, String userId, String remoteAddress);

	PagedResult<MtCallReferenceListResponse> getCallsBasicResponse(
					MtCallFilter toFilter, String userId, String remoteAddr);

//	void updateMbfStatus(String id, MbfStatus mbfStatus);
//
//	void updateSubscriberStatus(String id, SubscriberStatus subscriberStatus);
//
//	void updateResponseData(String id, String toJson, ResponseStatus answerReceived);
}
