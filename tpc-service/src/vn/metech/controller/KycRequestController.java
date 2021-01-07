package vn.metech.controller;

import org.springframework.web.bind.annotation.*;

import vn.metech.constant.Auth;
import vn.metech.constant.RequestType;
import vn.metech.dto.request.MtInfoDiscoveryFilterRequest;
import vn.metech.dto.request.MtInfoDiscoveryRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.InfoDiscoveryRequestDuplicateException;
import vn.metech.service.IInfoDiscoveryRequestService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/kyc/02/request")
public class KycRequestController {

	private IInfoDiscoveryRequestService infoDiscoveryRequestService;

	public KycRequestController(IInfoDiscoveryRequestService infoDiscoveryRequestService) {
		this.infoDiscoveryRequestService = infoDiscoveryRequestService;
	}

	@PostMapping({"/info-discovery"})
	public ActionResult receiveKyc02Request(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtInfoDiscoveryRequest mtInfoDiscoveryRequest)
					throws InfoDiscoveryRequestDuplicateException, BalanceNotEnoughException {
		infoDiscoveryRequestService.receivedCustomerRequest(
						mtInfoDiscoveryRequest, RequestType.KYC_REQUEST, userId, remoteAddr
		);
		return new ActionResult(mtInfoDiscoveryRequest.getRequestId());
	}

	@PostMapping("/filters")
	public ActionResult filterInfoDiscoveryRequests(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtInfoDiscoveryFilterRequest infoDiscoveryFilterRequest) {
		return new ActionResult(
						infoDiscoveryRequestService.filtersRequestsBy(
										infoDiscoveryFilterRequest.toFilter(), RequestType.KYC_REQUEST, userId
						)
		);
	}
}
