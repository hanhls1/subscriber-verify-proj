package vn.metech.service;

import vn.metech.dto.MtAdFilter;
import vn.metech.dto.response.MtAdResponse;
import vn.metech.entity.AdResponse;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface IAdResponseService extends IService<AdResponse> {

    PagedResult<MtAdResponse> getImeiResponsesFrom(MtAdFilter adFilter, String userId);

//    void updateMbfStatus(String id, MbfStatus mbfStatus);
//
//    void updateSubscriberStatus(String id, vn.metech.constant.SubscriberStatus subscriberStatus);
//
//    void updateResponseData(String id, String jsonData, ResponseStatus responseStatus);

    //FC02Response getResponse(String customerRequestId, String userId) throws RequestNotFoundException;
}
