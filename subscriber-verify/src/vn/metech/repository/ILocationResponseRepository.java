package vn.metech.repository;

import vn.metech.constant.RequestType;
import vn.metech.dto.MtLocationFilter;
import vn.metech.entity.LocationResponse;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface ILocationResponseRepository extends IRepository<LocationResponse> {

    PagedResult<LocationResponse> getResponsesBy(MtLocationFilter filter, RequestType requestType, List<String> userIds);

}
