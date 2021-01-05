package vn.metech.service;


import vn.metech.dto.request.RequestBase;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.exception.aio.ParamInvalidException;
import vn.metech.exception.aio.RequestInvalidException;
import vn.metech.exception.aio.RequestNotFoundException;
import vn.metech.service.base.IService;

import java.util.List;

public interface IConfirmInfoReceiveService extends IService<ConfirmInfoReceive> {

    ActionResult<? extends HashResponseBase>
    findResponse(RequestBase request, String userId) throws ParamInvalidException, RequestNotFoundException;

    HashResponseBase getResponseOf(RequestBase request, List<String> userIds) throws RequestInvalidException, RequestNotFoundException;

}
