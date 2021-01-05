package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.SubPartnerCreateRequest;
import vn.metech.dto.request.SubPartnerFilterRequest;
import vn.metech.dto.request.SubPartnerUpdateRequest;
import vn.metech.exception.PartnerNotFoundException;
import vn.metech.service.ISubPartnerService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/sub-partner")
public class SubPartnerController {

	private ISubPartnerService subPartnerService;

	public SubPartnerController(ISubPartnerService subPartnerService) {
		this.subPartnerService = subPartnerService;
	}

	@PostMapping("/filters")
	public ActionResult getPartnersFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody SubPartnerFilterRequest partnerFilterRequest) throws PartnerNotFoundException {
		return new ActionResult(subPartnerService.fillSubPartnersBy(partnerFilterRequest, userId));
	}

	@PostMapping("/create")
	public ActionResult createSubPartner(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody SubPartnerCreateRequest subPartnerCreateRequest) throws PartnerNotFoundException {
		return new ActionResult(subPartnerService.createSubPartner(subPartnerCreateRequest, userId));
	}

	@PostMapping("/update")
	public ActionResult updateSubPartner(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody SubPartnerUpdateRequest subPartnerUpdateRequest) throws PartnerNotFoundException {
		return new ActionResult(subPartnerService.updateSubPartner(subPartnerUpdateRequest, userId));
	}

	@PostMapping("get-list")
	public ActionResult getPartnersWithPaging(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody PartnerPageRequest partnerPageRequest) {
		return new ActionResult(subPartnerService.getPartnersWithPaging(partnerPageRequest, userId));
	}
}
