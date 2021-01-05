package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.fc01.MfsFc01Request;
import vn.metech.dto.request.fc01.MfsFc01ResultRequest;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.exception.aio.*;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/fc01")
public class FC01RestController {

    private final IConfirmInfoService confirmInfoService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public FC01RestController(IConfirmInfoService confirmInfoService,
                              IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.confirmInfoService = confirmInfoService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;
    }

    @PostMapping({"/request"})
    public ActionResult<? extends HashResponseBase> receiveFC01(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsFc01Request fc01Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(fc01Request, userId, false);
    }

    @PostMapping({"/response"})
    public ActionResult<? extends HashResponseBase>
    findResponse(@RequestHeader("user-id") String userId,
                 @Valid @RequestBody MfsFc01ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }
}
