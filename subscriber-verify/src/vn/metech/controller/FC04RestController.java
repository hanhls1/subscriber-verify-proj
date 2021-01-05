package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.fc04.MfsFc0401Request;
import vn.metech.dto.request.fc04.MfsFc0401ResultRequest;
import vn.metech.dto.request.fc04.MfsFc0402Request;
import vn.metech.dto.request.fc04.MfsFc0402ResultRequest;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.exception.aio.*;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/fc04")
public class FC04RestController {

    private final IConfirmInfoService confirmInfoService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public FC04RestController(IConfirmInfoService confirmInfoService,
                              IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.confirmInfoService = confirmInfoService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;
    }

    @PostMapping({"/01/request"})
    public ActionResult<? extends HashResponseBase> receiveFC0401(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsFc0401Request mfsFC0401Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsFC0401Request, userId, false);
    }

    @PostMapping({"/01/response"})
    public ActionResult<? extends HashResponseBase>
    find01Response(@RequestHeader("user-id") String userId,
                   @Valid @RequestBody MfsFc0401ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }

    @PostMapping({"/02/request"})
    public ActionResult<? extends HashResponseBase> receiveFC0402(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsFc0402Request mfsFC0402Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsFC0402Request, userId, false);
    }

    @PostMapping({"/02/response"})
    public ActionResult<? extends HashResponseBase>
    find02Response(@RequestHeader("user-id") String userId,
                   @Valid @RequestBody MfsFc0402ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }
}
