package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.MtTpcConfirmFilterRequest;
import vn.metech.service.TpcConfirmService;
import vn.metech.shared.ActionResult;

@RestController
@RequestMapping("/tpc/summary/response")
public class TpcSummaryResponseController {

	private TpcConfirmService tpcConfirmService;

	public TpcSummaryResponseController(TpcConfirmService tpcConfirmService) {
		this.tpcConfirmService = tpcConfirmService;
	}

	@PostMapping("/filters")
	public ActionResult getInfoDiscoveryResponses(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody MtTpcConfirmFilterRequest tpcConfirmFilterRequest) {
		return new ActionResult(tpcConfirmService.getResponsesBy(tpcConfirmFilterRequest.toFilter(), userId));
	}
}
