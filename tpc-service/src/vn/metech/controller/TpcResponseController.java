package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.constant.RequestType;
import vn.metech.dto.request.MtInfoDiscoveryFilterRequest;
import vn.metech.service.IInfoDiscoveryResponseService;
import vn.metech.shared.ActionResult;

@RestController
@RequestMapping("/tpc/response")
public class TpcResponseController {

	private IInfoDiscoveryResponseService infoDiscoveryResponseService;

	public TpcResponseController(
					IInfoDiscoveryResponseService infoDiscoveryResponseService) {
		this.infoDiscoveryResponseService = infoDiscoveryResponseService;
	}

	@PostMapping("/filters")
	public ActionResult getInfoDiscoveryResponses(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody MtInfoDiscoveryFilterRequest infoDiscoveryFilterRequest) {
		return new ActionResult(infoDiscoveryResponseService.getResponsesBy(
						infoDiscoveryFilterRequest.toFilter(), RequestType.TPC_REQUEST, userId
		));
	}
}
