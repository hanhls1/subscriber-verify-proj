package vn.metech.service;

import vn.metech.dto.MtIdFilter;
import vn.metech.dto.response.MtIdResponse;
import vn.metech.entity.IdResponse;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface IIdResponseService extends IService<IdResponse> {

	PagedResult<MtIdResponse> getPastIdResponsesFrom(MtIdFilter idFilter, String userId);

//	void updateMbfStatus(String responseId, MbfStatus mbfStatus);
//
//	void updateSubscriberStatus(String responseId, SubscriberStatus subscriberStatus);
}
