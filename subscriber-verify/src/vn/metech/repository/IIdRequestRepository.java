package vn.metech.repository;

import vn.metech.constant.RequestStatus;
import vn.metech.dto.MtIdFilter;
import vn.metech.dto.request.MtIdRequest;
import vn.metech.entity.IdRequest;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.Date;
import java.util.List;

public interface IIdRequestRepository extends IRepository<IdRequest> {


	List<IdRequest> getRequestsIncludeResponseBy(
					int limit,
					Date fetchAfter,
					int fetchTimesLimit,
					boolean dupExclude,
					RequestStatus... requestStatus);

	PagedResult<IdRequest> getRequestsIncludeResponseBy(MtIdFilter idFilter, List<String> userIds);

	PagedResult<IdRequest> getRequestsBy(MtIdFilter idFilter, List<String> userIds);

	IdRequest findDuplicateIdRequestIncludeResponseBy(MtIdRequest mtRequest, Date checkDuplicateAfter);
}
