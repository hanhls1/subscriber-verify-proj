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
@RequestMapping("/tpc/request")
public class TpcRequestController {

	private IInfoDiscoveryRequestService infoDiscoveryRequestService;

	public TpcRequestController(
					IInfoDiscoveryRequestService infoDiscoveryRequestService) {
		this.infoDiscoveryRequestService = infoDiscoveryRequestService;
	}

	@PostMapping({"/info-discovery"})
	public ActionResult receiveInfoDiscoveryRequestFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtInfoDiscoveryRequest mtInfoDiscoveryRequest)
					throws InfoDiscoveryRequestDuplicateException, BalanceNotEnoughException {
		infoDiscoveryRequestService.receivedCustomerRequest(
						mtInfoDiscoveryRequest, RequestType.TPC_REQUEST, userId, remoteAddr
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
										infoDiscoveryFilterRequest.toFilter(), RequestType.TPC_REQUEST, userId
						)
		);
	}

}
