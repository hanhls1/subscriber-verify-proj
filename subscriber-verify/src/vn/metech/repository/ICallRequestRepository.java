package vn.metech.repository;

import vn.metech.constant.RequestType;
import vn.metech.dto.MtCallFilter;
import vn.metech.dto.request.MtCallRequest;
import vn.metech.entity.CallRequest;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.Date;
import java.util.List;

public interface ICallRequestRepository extends IRepository<CallRequest> {

	CallRequest findDuplicateCallRequestIncludeResponseBy(MtCallRequest mtCallRequest, Date checkAfter);

	PagedResult<CallRequest> getRequestsBy(MtCallFilter filter, List<String> userIds);

	PagedResult<CallRequest> getRequestsIncludeResponseBy(
					MtCallFilter filter, RequestType requestType, List<String> userIds);

	List<CallRequest> getGroupSyncibleRequestsIncludeResponseBy(int numberOfRecords, List<String> userIds);

}
