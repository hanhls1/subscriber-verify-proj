package vn.metech.service;

import vn.metech.constant.RequestType;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.dto.request.MtResponseRequest;
import vn.metech.dto.response.MtInfoDiscoveryResponse;
import vn.metech.dto.response.MtKyc02Response;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.exception.NoHaveResultException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface IInfoDiscoveryResponseService extends IService<InfoDiscoveryResponse> {

	InfoDiscoveryResponse getQuestionSentResponseIncludeRequestsBy(String responseId);

	PagedResult<MtInfoDiscoveryResponse> getResponsesBy(
					MtInfoDiscoveryFilter infoDiscoveryFilter, RequestType requestType, String userId);

	MtKyc02Response findResponseBy(MtResponseRequest mtResponseRequest, String userId) throws NoHaveResultException;
}
