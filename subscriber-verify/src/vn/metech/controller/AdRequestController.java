package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.constant.VerifyService;
import vn.metech.dto.request.MtAdFilterRequest;
import vn.metech.dto.request.MtAdRequest;
import vn.metech.exception.AdRequestDuplicateException;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.service.IAdRequestService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/ad-reference/request")
public class AdRequestController {

	private IAdRequestService adRequestService;

	public AdRequestController(IAdRequestService adRequestService) {
		this.adRequestService = adRequestService;
	}

	@PostMapping({"/simple"})
	public ActionResult createSimpleImeiRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtAdRequest mtAdRequest)
					throws AdRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {
		adRequestService.receivedCustomerRequest(mtAdRequest, VerifyService.FC02_01, userId, remoteAddr);
		return new ActionResult(mtAdRequest.getRequestId());
	}

	@PostMapping({"/create", "/advance"})
	public ActionResult createAdvanceImeiRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtAdRequest mtAdRequest)
					throws AdRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {
		adRequestService.receivedCustomerRequest(mtAdRequest, VerifyService.FC02_02, userId, remoteAddr);
		return new ActionResult(mtAdRequest.getRequestId());
	}

	@PostMapping("/filters")
	public ActionResult filterAdRequests(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtAdFilterRequest mtAdFilterRequest) {
		return new ActionResult(adRequestService.getRequestsFrom(mtAdFilterRequest, userId));
	}
}
