package vn.metech.repository;

import vn.metech.constant.RequestType;
import vn.metech.dto.request.MtLocationFilterRequest;
import vn.metech.dto.request.MtLocationRequest;
import vn.metech.entity.LocationRequest;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.Date;
import java.util.List;

public interface ILocationRequestRepository extends IRepository<LocationRequest> {

	PagedResult<LocationRequest> getRequestsBy(
					MtLocationFilterRequest locationFilter, RequestType requestType, List<String> userIds);

	LocationRequest findDuplicateLocationRequestIncludeResponseBy(MtLocationRequest mtRequest, Date checkDuplicateAfter);

}
