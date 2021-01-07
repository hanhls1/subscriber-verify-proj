package vn.metech.service;

import vn.metech.constant.RequestStatus;
import vn.metech.constant.RequestType;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.dto.request.MtInfoDiscoveryRequest;
import vn.metech.dto.response.MtInfoDiscoveryRequestListResponse;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.InfoDiscoveryRequestDuplicateException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface IInfoDiscoveryRequestService extends IService<InfoDiscoveryRequest> {

	InfoDiscoveryRequest receivedCustomerRequest(
					MtInfoDiscoveryRequest mtInfoDiscoveryRequest, RequestType requestType,
					String userId, String remoteAddr) throws InfoDiscoveryRequestDuplicateException, BalanceNotEnoughException;


	List<InfoDiscoveryRequest> getPendingRequestsIncludeResponseWithLimit();

	List<InfoDiscoveryRequest> getAnswerReceivedRequestsIncludeResponseWithLimit();

	InfoDiscoveryRequest getRequestIncludeResponseBy(
					String requestId, RequestStatus requestStatus);

	List<InfoDiscoveryRequest> getTimeoutRequestsIncludeResponseWithLimit();

	List<InfoDiscoveryRequest> getChargeableRequestsIncludeResponseWithLimit();

	InfoDiscoveryRequest getQuestionSentRequestIncludeResponseBy(String requestId);

	PagedResult<MtInfoDiscoveryRequestListResponse> filtersRequestsBy(
					MtInfoDiscoveryFilter toFilter, RequestType requestType, String userId);
}
