package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.RequestBase;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.exception.aio.ParamInvalidException;
import vn.metech.exception.aio.RequestInvalidException;
import vn.metech.exception.aio.RequestNotFoundException;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.LocalService;

import javax.validation.Valid;

@RestController
@RequestMapping({"/vpb"})
public class VPBRestController {

    private final LocalService localService;
    private final IConfirmInfoReceiveService confirmInfoReceiveService;

    public VPBRestController(
            LocalService localService,
            IConfirmInfoReceiveService confirmInfoReceiveService) {
        this.localService = localService;
        this.confirmInfoReceiveService = confirmInfoReceiveService;
    }

    @PostMapping({"/confirm-info"})
    public ActionResult<? extends HashResponseBase>
    findResponse(@RequestHeader("user-id") String userId,
                 @Valid @RequestBody RequestBase requestBase)
            throws ParamInvalidException, RequestNotFoundException, RequestInvalidException {
        if (!localService.canAccessTo(userId, requestBase.getServiceType().name())) {
            throw new RequestInvalidException(requestBase, requestBase.id(), "Request chưa được chấp nhận bởi hệ thống");
        }
        return confirmInfoReceiveService.findResponse(requestBase, userId);
    }
}
