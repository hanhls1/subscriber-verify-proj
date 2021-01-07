package vn.metech.controller;

import org.springframework.web.bind.annotation.*;

import vn.metech.constant.Auth;
import vn.metech.dto.request.MtResponseRequest;
import vn.metech.exception.NoHaveResultException;
import vn.metech.service.IInfoDiscoveryResponseService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/kyc/02/response")
public class KycResponseController {

	private IInfoDiscoveryResponseService infoDiscoveryResponseService;

	public KycResponseController(IInfoDiscoveryResponseService infoDiscoveryResponseService) {
		this.infoDiscoveryResponseService = infoDiscoveryResponseService;
	}

	@PostMapping("/require-result")
	public ActionResult getKyc02Response(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtResponseRequest mtResponseRequest) throws NoHaveResultException {
		return new ActionResult(infoDiscoveryResponseService.findResponseBy(mtResponseRequest, userId));
	}
}
