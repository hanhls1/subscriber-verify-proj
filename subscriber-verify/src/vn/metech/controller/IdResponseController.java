package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.MtIdFilterRequest;
import vn.metech.service.IIdResponseService;
import vn.metech.shared.ActionResult;

@RestController
@RequestMapping("/id-reference/response")
public class IdResponseController {

	private IIdResponseService idResponseService;

	public IdResponseController(IIdResponseService idResponseService) {
		this.idResponseService = idResponseService;
	}

	@PostMapping("past-id-numbers")
	public ActionResult getMtIdNumberResponsesFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody MtIdFilterRequest idRequest) {
		return new ActionResult(idResponseService.getPastIdResponsesFrom(idRequest.toFilter(), userId));
	}
}
