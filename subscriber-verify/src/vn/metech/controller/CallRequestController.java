package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.constant.RequestType;
import vn.metech.constant.VerifyService;
import vn.metech.dto.request.MtCallFilterRequest;
import vn.metech.dto.request.MtCallRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CallRequestDuplicateException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.service.ICallRequestService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("call-reference/request")
public class CallRequestController {

	private ICallRequestService callRequestService;

	public CallRequestController(ICallRequestService callRequestService) {
		this.callRequestService = callRequestService;
	}

	@PostMapping({"/basic-call-verify", "/simple"})
	public ActionResult receiveBasicCallVerifyRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtCallRequest mtCallRequest)
					throws CallRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {
		mtCallRequest.setBasic(true);
		callRequestService.receivedCustomerRequest(
						mtCallRequest, VerifyService.FC04_02, RequestType.BASIC, userId, remoteAddr);
		return new ActionResult(mtCallRequest.getRequestId());
	}

	@PostMapping({"/advance"})
	public ActionResult receiveAdvanceCallVerifyRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtCallRequest mtCallRequest)
					throws CallRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {
		mtCallRequest.setBasic(true);
		callRequestService.receivedCustomerRequest(
						mtCallRequest, VerifyService.FC04_01, RequestType.VERIFY, userId, remoteAddr);
		return new ActionResult(mtCallRequest.getRequestId());
	}

	@PostMapping({"/filters"})
	public ActionResult filterLocationVerifyRequests(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody MtCallFilterRequest callFilter) {
		return new ActionResult(
						callRequestService.fillCallReferenceRequestBy(callFilter.toFilter(), userId, remoteAddr)
		);
	}
}
