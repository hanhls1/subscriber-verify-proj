package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.RequestBase;
import vn.metech.dto.request.aio.ConfirmInfoListRequest;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.aio.ConfirmInfoRequestResponse;
import vn.metech.exception.aio.*;
import vn.metech.service.ConfirmInfoSearchService;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;


@RestController
@RequestMapping("/aio")
public class AIORestController {

    private final IConfirmInfoService confirmInfoService;
    private final ConfirmInfoSearchService confirmInfoSearchService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public AIORestController(
            IConfirmInfoService confirmInfoService,
            ConfirmInfoSearchService confirmInfoSearchService,
            IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.confirmInfoService = confirmInfoService;
        this.confirmInfoSearchService = confirmInfoSearchService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;

    }

    @PostMapping({"/request"})
    public ActionResult<? extends HashResponseBase>
    sendRequest(@RequestHeader("user-id") String userId,
                @Valid @RequestBody RequestBase requestBase)
            throws ParamInvalidException, RequestReferenceInvalidException,
            BalanceNotEnoughException, RequestDuplicateException, RequestInvalidException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(requestBase, userId, true);
    }

    @PostMapping({"/find-response"})
    public ActionResult<? extends HashResponseBase>
    findResponse(@RequestHeader("user-id") String userId,
                 @Valid @RequestBody RequestBase requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }

    @PostMapping({"/list-request"})
    public PageResponse<ConfirmInfoRequestResponse> findByConfirmInfoListRequest(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody ConfirmInfoListRequest confirmInfoListRequest) {
        return confirmInfoSearchService.findBy(confirmInfoListRequest, userId);
    }

}
