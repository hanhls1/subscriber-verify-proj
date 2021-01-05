package vn.metech.repository;

import vn.metech.constant.RequestType;
import vn.metech.dto.MtCallFilter;
import vn.metech.entity.CallResponse;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface ICallResponseRepository extends IRepository<CallResponse> {

//	PagedResult<CallResponse> getResponsesIncludeRequestBy(MtCallFilter filter, String userId);
//
//	CallResponse getResponseIncludeRequestsBy(String responseId, ResponseStatus... statuses);

	PagedResult<CallResponse> getResponsesBy(
					MtCallFilter toFilter, RequestType requestType, List<String> userIds);

//	void updateSubscriberStatus(String requestId, SubscriberStatus subscriberStatus);
//
//	void updateMbfStatus(String requestId, MbfStatus mbfStatus);
//
//	void updateResponseData(String id, String jsonData, ResponseStatus responseStatus);
}
