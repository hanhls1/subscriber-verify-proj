package vn.metech.repository;

import vn.metech.constant.RequestType;
import vn.metech.constant.ResponseStatus;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface IInfoDiscoveryResponseRepository extends IRepository<InfoDiscoveryResponse> {

	InfoDiscoveryResponse getResponseIncludeRequestsBy(String responseId, ResponseStatus... statuses);

	PagedResult<InfoDiscoveryResponse> getResponsesBy(
					MtInfoDiscoveryFilter filter, RequestType requestType, List<String> userIds);

}
