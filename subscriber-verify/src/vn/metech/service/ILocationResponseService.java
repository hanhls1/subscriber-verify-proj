package vn.metech.service;

import vn.metech.dto.request.MtRegularlyLocationRequest;
import vn.metech.dto.response.MtAdvanceLocationResponse;
import vn.metech.entity.LocationResponse;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;


public interface ILocationResponseService extends IService<LocationResponse> {

    PagedResult<MtAdvanceLocationResponse> getAdvanceLocationResponses(
            MtRegularlyLocationRequest mtLocationRequest, String userId, String remoteAddress);

//    void updateMbfStatus(String responseId, MbfStatus mbfStatus);
//
//    void updateSubscriberStatus(String responseId, SubscriberStatus subscriberStatus);
}
