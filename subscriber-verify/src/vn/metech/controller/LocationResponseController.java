package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.MtRegularlyLocationRequest;
import vn.metech.service.ILocationResponseService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/location-verify/response")
public class LocationResponseController {

	private ILocationResponseService locationResponseService;

	public LocationResponseController(ILocationResponseService locationResponseService) {
		this.locationResponseService = locationResponseService;
	}

//	@PostMapping("/regularly-locations")
//	public ActionResult getRegularlyLocationResponses(
//					@RequestHeader(Auth.USER_KEY) String userId,
//					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
//					@Valid @RequestBody MtRegularlyLocationRequest locationRequest) {
//
//		return new ActionResult(
//						locationResponseService
//										.getRegularlyLocationResponsesFrom(
//														locationRequest,
//														userId,
//														remoteAddr
//										)
//		);
//	}
//
//
//	@PostMapping("/most-visited-locations")
//	public ActionResult getMostVisitedLocationResponses(
//					@RequestHeader(Auth.USER_KEY) String userId,
//					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
//					@Valid @RequestBody MtRegularlyLocationRequest locationRequest) {
//
//		return new ActionResult(
//						locationResponseService
//										.getMostVisitedResponseFrom(
//														locationRequest,
//														userId,
//														remoteAddr
//										)
//		);
//	}

	@PostMapping("/advance-filters")
	public ActionResult filterAdvanceLocationResponses(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody MtRegularlyLocationRequest locationRequest) {
		return new ActionResult(locationResponseService.getAdvanceLocationResponses(locationRequest, userId, remoteAddr));
	}
}
