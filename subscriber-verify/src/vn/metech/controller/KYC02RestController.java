package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.RequestBase;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.exception.aio.*;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/kyc02")
public class KYC02RestController {

    private final IConfirmInfoService confirmInfoService;

    public KYC02RestController(IConfirmInfoService confirmInfoService) {
        this.confirmInfoService = confirmInfoService;
    }

    @PostMapping({"/request"})
    public ActionResult<? extends HashResponseBase> receiveKyc02Request(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody RequestBase requestBase)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(requestBase, userId, false);
    }
}
