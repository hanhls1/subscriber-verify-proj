package vn.metech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.metech.service.IRedisService;
import vn.metech.shared.ActionResult;

@RestController
@RequestMapping("/cache")
public class CacheController {

	private IRedisService authRedisService;

	public CacheController(IRedisService authRedisService) {
		this.authRedisService = authRedisService;
	}

	@GetMapping("/refresh")
	public ActionResult refreshCache() {
		authRedisService.loadPartnersIntoRedis();

		return new ActionResult();
	}
}
