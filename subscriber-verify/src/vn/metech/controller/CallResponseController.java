package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.MtCallFilterRequest;
import vn.metech.service.ICallResponseService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/call-reference/response")
public class CallResponseController {

	private ICallResponseService callResponseService;

	public CallResponseController(ICallResponseService callResponseService) {
		this.callResponseService = callResponseService;
	}

	@PostMapping("/calls-advance")
	public ActionResult getCallsVerifyResponseFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody MtCallFilterRequest callRequest) {

		return new ActionResult(callResponseService.getMtCallsVerifyResponseFrom(callRequest, userId, remoteAddr));
	}

//	@PostMapping("/calls-verify")
//	public ActionResult getCallsVerifyResponseFrom(
//					@RequestHeader(Auth.USER_KEY) String userId,
//					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
//					@Valid @RequestBody MtCallFilterRequest callRequest) {
//
//		return new ActionResult(
//						callResponseService
//										.getMtCallsVerifyResponseFrom(callRequest, userId, remoteAddr)
//		);
//	}

	@PostMapping("/calls-basic")
	public ActionResult getCallsBasicResponse(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody MtCallFilterRequest mtCallFilterRequest) {
		return new ActionResult(
						callResponseService
										.getCallsBasicResponse(mtCallFilterRequest.toFilter(), userId, remoteAddr)
		);
	}
}
