package vn.metech.repository;

import vn.metech.constant.RequestStatus;
import vn.metech.dto.MtAdFilter;
import vn.metech.dto.request.MtAdRequest;
import vn.metech.entity.AdRequest;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.Date;
import java.util.List;

public interface IAdRequestRepository extends IRepository<AdRequest> {

	long countRequestsBy(String customerRequestId);

	AdRequest findDuplicateAdRequestIncludeResponseBy(MtAdRequest mtAdRequest, Date checkAfter);

	List<AdRequest> getRequestsIncludeResponseBy(
					int limit,
					Date fetchAfter,
					int fetchLimit,
					RequestStatus... requestStatus);


	AdRequest getRequestBy(String requestId, RequestStatus... statuses);

	PagedResult<AdRequest> getRequestsIncludeResponseBy(MtAdFilter adFilter, List<String> userIds);

	PagedResult<AdRequest> getRequestsBy(MtAdFilter mtAdFilter, List<String> userIds);

	List<AdRequest> getGroupSyncibleRequestsIncludeResponseBy(int numberOfRecords, List<String> userIds);

}
