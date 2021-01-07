package vn.metech.repository;

import vn.metech.constant.RequestStatus;
import vn.metech.constant.RequestType;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.dto.request.MtInfoDiscoveryRequest;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.Date;
import java.util.List;

public interface IInfoDiscoveryRequestRepository extends IRepository<InfoDiscoveryRequest> {

	long countRequestsBy(String customerRequestId);

	InfoDiscoveryRequest getRequestBy(MtInfoDiscoveryRequest mtInfoDiscoveryRequest, RequestStatus... status);

	InfoDiscoveryRequest getRequestIncludeResponseBy(
					MtInfoDiscoveryRequest mtInfoDiscoveryRequest, RequestStatus... statuses);

	List<InfoDiscoveryRequest> getRequestsBy(int limit, RequestStatus... requestStatus);

	List<InfoDiscoveryRequest> getRequestsIncludeResponseBy(
					int limit, Date fetchAfter, int fetchTimesLimit,
					boolean dupExclude, RequestStatus... requestStatus);

	InfoDiscoveryRequest getRequestIncludeResponseBy(String requestId, RequestStatus... statuses);

	InfoDiscoveryRequest getRequestBy(String requestId, RequestStatus... statuses);

	InfoDiscoveryRequest getCustomerRequestIncludeResponseBy(
					String customerRequestId, List<String> userIds, RequestStatus... statuses);

	List<InfoDiscoveryRequest> getRequestsIncludeResponseBy(int limit, Date createdBefore, RequestStatus... statuses);

	List<InfoDiscoveryRequest> getRequestsIncludeResponseBy(
					int limit, Date createdBefore, Boolean charged, RequestStatus... statuses);

	PagedResult<InfoDiscoveryRequest> getRequestsBy(
					MtInfoDiscoveryFilter infoDiscoveryFilter, RequestType requestType, List<String> userIds);
}
