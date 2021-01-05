package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.MtAdFilterRequest;
import vn.metech.service.IAdResponseService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/ad-reference/response")
public class AdResponseController {

	private IAdResponseService adResponseService;

	public AdResponseController(IAdResponseService adResponseService) {
		this.adResponseService = adResponseService;
	}

	@PostMapping("/filters")
	public ActionResult getCurrentImeiResponsesFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody MtAdFilterRequest adRequest) {
		return new ActionResult(adResponseService.getImeiResponsesFrom(adRequest.toFilter(), userId));
	}

	//@GetMapping("/find")
	//public ActionResult getResponse(
	//				@RequestHeader(Auth.USER_KEY) String userId,
	//				@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
	//				@RequestParam(name = "q") String customerRequestId) {
	//	return new ActionResult(adResponseService.getResponse(customerRequestId, userId));
	//}
}
