package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.fc03.MfsKyc03Request;
import vn.metech.dto.request.fc03.MfsKyc03ResultRequest;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.exception.aio.*;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/kyc03")
public class KYC03RestController {

    private final IConfirmInfoService confirmInfoService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public KYC03RestController(IConfirmInfoService confirmInfoService,
                               IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.confirmInfoService = confirmInfoService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;
    }

    @PostMapping({"/request"})
    public ActionResult<? extends HashResponseBase> receiveFC0301(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsKyc03Request mfsKyc03Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsKyc03Request, userId, false);
    }

    @PostMapping({"response"})
    public ActionResult<? extends HashResponseBase>
    findResponse(@RequestHeader("user-id") String userId,
                 @Valid @RequestBody MfsKyc03ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }
}
