package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.constant.VerifyService;
import vn.metech.dto.request.MtIdFilterRequest;
import vn.metech.dto.request.MtIdRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.exception.IdRequestDuplicateException;
import vn.metech.service.IIdRequestService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/id-reference/request")
public class IdRequestController {

	private IIdRequestService idRequestService;

	public IdRequestController(IIdRequestService idRequestService) {
		this.idRequestService = idRequestService;
	}

	@PostMapping({"/verify"})
	public ActionResult receiveIdVerifyRequestFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtIdRequest mtIdRequest)
					throws IdRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {
		idRequestService.receivedCustomerRequest(mtIdRequest, VerifyService.FC01, userId, remoteAddr);
		return new ActionResult(mtIdRequest.getRequestId());
	}

	@PostMapping("filters")
	public ActionResult filterIdRequestsFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtIdFilterRequest mtIdRequest) {
		return new ActionResult(idRequestService.getRequestsFrom(mtIdRequest.toFilter(), userId));
	}
}
