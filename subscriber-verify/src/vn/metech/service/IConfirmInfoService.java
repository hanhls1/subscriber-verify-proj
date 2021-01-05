package vn.metech.service;

import vn.metech.dto.request.RequestBase;
import vn.metech.dto.request.monitor.ConfirmRequest;
import vn.metech.dto.request.monitor.FilterRequest;
import vn.metech.dto.response.*;
import vn.metech.entity.ConfirmInfo;
import vn.metech.exception.aio.*;
import vn.metech.service.base.IService;

import java.util.List;

public interface IConfirmInfoService extends IService<ConfirmInfo> {

    ActionResult<? extends HashResponseBase> receiveConfirmInfoRequest(RequestBase request, String userId, boolean aio)
            throws ParamInvalidException, RequestInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException;

    /////////
    PageResponse<MonitorResponse> getFilRequest(RequestBase req, ConfirmRequest confirmRequest, String userId) throws RequestDuplicateException;

    PageResponse<ConfirmInfoResponse> getFilter(RequestBase req, FilterRequest filter, String userId) throws RequestDuplicateException;


    List<StatusResponse> getAllStatus();

    List<String> getListServices();

    List<PartnerResponse> getListPartner();

    List<SubPartnerResponse> getListSubPartner(String partnerId);

}
