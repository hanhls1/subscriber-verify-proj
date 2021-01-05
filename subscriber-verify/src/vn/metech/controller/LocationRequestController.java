package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.constant.RequestType;
import vn.metech.dto.request.MtLocationFilterRequest;
import vn.metech.dto.request.MtLocationRequest;
import vn.metech.exception.BalanceNotEnoughException;
import vn.metech.exception.CustomerCodeNotFoundException;
import vn.metech.exception.LocationRequestDuplicateException;
import vn.metech.service.ILocationRequestService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/location-verify/request")
public class LocationRequestController {

	private ILocationRequestService locationRequestService;

	public LocationRequestController(ILocationRequestService locationRequestService) {
		this.locationRequestService = locationRequestService;
	}

	@Deprecated
	@PostMapping({"/regularly", "/"})
	public ActionResult receiveLocationVerifyRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtLocationRequest mtLocationRequest)
					throws LocationRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {

		locationRequestService.receivedCustomerRequest(mtLocationRequest, RequestType.REGULARLY, userId, remoteAddr);

		return new ActionResult(mtLocationRequest.getRequestId());
	}

	@Deprecated
	@PostMapping({"/most-visited"})
	public ActionResult receiveMostVisitedLocationRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtLocationRequest mtLocationRequest)
					throws LocationRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {

		locationRequestService.receivedCustomerRequest(mtLocationRequest, RequestType.MOST_VISITED, userId, remoteAddr);

		return new ActionResult(mtLocationRequest.getRequestId());
	}

	@PostMapping({"/advance-verify"})
	public ActionResult receiveAdvanceLocationRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtLocationRequest mtLocationRequest)
					throws LocationRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {

		locationRequestService.receivedCustomerRequest(
						mtLocationRequest, RequestType.TPC_LOCATION_ADVANCE_VERIFY, userId, remoteAddr);

		return new ActionResult(mtLocationRequest.getRequestId());
	}

	@PostMapping({"/basic-verify"})
	public ActionResult receiveBasicLocationRequest(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid MtLocationRequest mtLocationRequest)
					throws LocationRequestDuplicateException, CustomerCodeNotFoundException, BalanceNotEnoughException {

		locationRequestService.receivedCustomerRequest(
						mtLocationRequest, RequestType.TPC_LOCATION_BASIC_VERIFY, userId, remoteAddr);

		return new ActionResult(mtLocationRequest.getRequestId());
	}

	@PostMapping({"/advance-filters", "filters"})
	public ActionResult filterAdvanceLocationVerifyRequests(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody MtLocationFilterRequest locationFilter) {

		return new ActionResult(
						locationRequestService.fillLocationRequestBy(
										locationFilter, RequestType.TPC_LOCATION_ADVANCE_VERIFY, userId, remoteAddr
						));
	}

}
