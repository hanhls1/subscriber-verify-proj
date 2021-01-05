package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.tariff.TariffCreateRequest;
import vn.metech.dto.tariff.TariffDetailResponse;
import vn.metech.dto.tariff.TariffFilterPageRequest;
import vn.metech.dto.tariff.TariffUpdateRequest;
import vn.metech.exception.tariff.TariffNotFoundException;
import vn.metech.service.ITariffService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/billing/tariff")
public class TariffController {

	private ITariffService tariffService;

	public TariffController(ITariffService tariffService) {
		this.tariffService = tariffService;
	}

	@PostMapping("/get-list")
	public ActionResult findTariffWithPaging(@RequestHeader(Auth.USER_KEY) String userId,
	                                         @RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
	                                         @RequestBody @Valid TariffFilterPageRequest tariffRequest) {
		return new ActionResult(tariffService.findTariffWithPaging(tariffRequest, userId, remoteAddr));
	}

	@PostMapping("/create")
	public ActionResult createTariff(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid TariffCreateRequest tariffRequest) {
		TariffDetailResponse response = tariffService.createTariff(tariffRequest, userId, remoteAddr);
		return new ActionResult(response);
	}

	@PostMapping("/update")
	public ActionResult updateTariff(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody @Valid TariffUpdateRequest tariffRequest) throws TariffNotFoundException {
		TariffDetailResponse response = tariffService.updateTariff(tariffRequest, userId, remoteAddr);
		return new ActionResult(response);
	}
}
