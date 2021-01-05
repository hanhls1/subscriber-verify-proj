package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.fc03.MfsFc03Request;
import vn.metech.dto.request.fc03.MfsFc03ResultRequest;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.exception.aio.*;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/fc03")
public class FC03RestController {

    private final IConfirmInfoService confirmInfoService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public FC03RestController(IConfirmInfoService confirmInfoService,
                              IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.confirmInfoService = confirmInfoService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;
    }

    @PostMapping({"/request"})
    public ActionResult<? extends HashResponseBase> receiveFC0301(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsFc03Request mfsFC03Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsFC03Request, userId, false);
    }

    @PostMapping({"response"})
    public ActionResult<? extends HashResponseBase>
    findResponse(@RequestHeader("user-id") String userId,
                 @Valid @RequestBody MfsFc03ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }
}
