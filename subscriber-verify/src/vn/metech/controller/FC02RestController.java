package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.common.ServiceType;
import vn.metech.dto.request.aio.ConfirmInfoListRequest;
import vn.metech.dto.request.fc02.*;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.aio.ConfirmInfoRequestResponse;
import vn.metech.dto.response.fc02.FC02ListResponse;
import vn.metech.exception.aio.*;
import vn.metech.service.ConfirmInfoSearchService;
import vn.metech.service.FC02SearchService;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.IConfirmInfoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/fc02")
public class FC02RestController {

    FC02SearchService fc02SearchService;
    ConfirmInfoSearchService confirmInfoSearchService;
    private final IConfirmInfoService confirmInfoService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public FC02RestController(
            FC02SearchService fc02SearchService,
            ConfirmInfoSearchService confirmInfoSearchService,
            IConfirmInfoService confirmInfoService,
            IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.fc02SearchService = fc02SearchService;
        this.confirmInfoSearchService = confirmInfoSearchService;
        this.confirmInfoService = confirmInfoService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;
    }

    @PostMapping({"/01/request"})
    public ActionResult<? extends HashResponseBase> receiveFC0201(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsFc0201Request mfsFc0201Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsFc0201Request, userId, false);
    }

    @PostMapping({"/01/response"})
    public ActionResult<? extends HashResponseBase>
    find01Response(@RequestHeader("user-id") String userId,
                   @Valid @RequestBody MfsFc0201ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }

    @PostMapping({"/02/request"})
    public ActionResult<? extends HashResponseBase> receiveFC0202(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody MfsFc0202Request mfsFc0202Request)
            throws RequestInvalidException, ParamInvalidException,
            RequestDuplicateException, RequestReferenceInvalidException, BalanceNotEnoughException, RequestTooManyException, SystemBusyException {
        return confirmInfoService.receiveConfirmInfoRequest(mfsFc0202Request, userId, false);
    }

    @PostMapping({"/02/response"})
    public ActionResult<? extends HashResponseBase>
    find02Response(@RequestHeader("user-id") String userId,
                   @Valid @RequestBody MfsFc0202ResultRequest requestBase) throws ParamInvalidException, RequestNotFoundException {
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }

    @PostMapping("/response/get-list")
    public PageResponse<FC02ListResponse> getListResponse(@RequestHeader("user-id") String userId,
                                                          @RequestBody FC02RequestFilter requestFilter) {
        return fc02SearchService.findResponses(requestFilter, userId);
    }

    @PostMapping({"request/get-list"})
    public PageResponse<ConfirmInfoRequestResponse> findByConfirmInfoListRequest(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody ConfirmInfoListRequest confirmInfoListRequest) {
        confirmInfoListRequest.setServiceType(ServiceType.FC_BS_IMEI_02);
        return confirmInfoSearchService.findBy(confirmInfoListRequest, userId);
    }
}
