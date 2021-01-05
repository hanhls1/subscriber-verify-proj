package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.PartnerCreateRequest;
import vn.metech.dto.request.PartnerFilterRequest;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.PartnerUpdateRequest;
import vn.metech.dto.response.PartnerListResponse;
import vn.metech.exception.PartnerDuplicateException;
import vn.metech.exception.PartnerNotFoundException;
import vn.metech.service.IPartnerService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/partner")
public class PartnerController {

	private IPartnerService partnerService;

	public PartnerController(IPartnerService partnerService) {
		this.partnerService = partnerService;
	}

	@PostMapping("/filters")
	public ActionResult getPartnersFrom(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody PartnerFilterRequest partnerFilterRequest) {
		return new ActionResult(partnerService.fillPartnersBy(partnerFilterRequest, userId));
	}

	@PostMapping("/create")
	public ActionResult createPartner(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody PartnerCreateRequest partnerCreateRequest) throws PartnerDuplicateException {
		return new ActionResult(partnerService.createPartner(partnerCreateRequest, userId));
	}

	@PostMapping("/update")
	public ActionResult updatePartner(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody PartnerUpdateRequest partnerUpdateRequest) throws PartnerNotFoundException {
		PartnerListResponse partnerListResponse = partnerService.updatePartner(partnerUpdateRequest, userId);
		return new ActionResult(partnerListResponse);
	}

	@PostMapping("get-list")
	public ActionResult getPartnersWithPaging(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody PartnerPageRequest partnerPageRequest) {
		return new ActionResult(partnerService.getPartnersWithPaging(partnerPageRequest, userId));
	}
}
