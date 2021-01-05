package vn.metech.controller;

import org.springframework.web.bind.annotation.*;

import vn.metech.dto.request.combo.*;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.exception.aio.*;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/combo")
public class ComboRestController {

    private IConfirmInfoService confirmInfoService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public ComboRestController(
            IConfirmInfoService confirmInfoService,
            IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.confirmInfoService = confirmInfoService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;
    }

    @PostMapping({"/01/request"})
    public ActionResult<? extends HashResponseBase> receiveCombo01(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsCombo01Request mfsCombo01Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsCombo01Request, userId, false);
    }

    @PostMapping({"/01/response"})
    public ActionResult<? extends HashResponseBase>
    findCB01Response(@RequestHeader("user-id") String userId,
                     @Valid @RequestBody MfsCombo01ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }

    @PostMapping({"/02/request"})
    public ActionResult<? extends HashResponseBase> receiveCombo02(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsCombo02Request mfsCombo02Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsCombo02Request, userId, false);
    }

    @PostMapping({"/02/response"})
    public ActionResult<? extends HashResponseBase>
    findCB02Response(@RequestHeader("user-id") String userId,
                     @Valid @RequestBody MfsCombo02ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }

    @PostMapping({"/03/request"})
    public ActionResult<? extends HashResponseBase> receiveCombo03(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsCombo03Request mfsCombo03Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsCombo03Request, userId, false);
    }

    @PostMapping({"/03/response"})
    public ActionResult<? extends HashResponseBase>
    findCB03Response(@RequestHeader("user-id") String userId,
                     @Valid @RequestBody MfsCombo03ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }

    @PostMapping({"/04/request"})
    public ActionResult<? extends HashResponseBase> receiveCombo04(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsCombo04Request mfsCombo04Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsCombo04Request, userId, false);
    }

    @PostMapping({"/04/response"})
    public ActionResult<? extends HashResponseBase>
    findCB04Response(@RequestHeader("user-id") String userId,
                     @Valid @RequestBody MfsCombo04ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }
}
