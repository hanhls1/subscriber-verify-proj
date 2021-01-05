package vn.metech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.metech.dto.response.EmailResponse;
import vn.metech.dto.response.PhoneResponse;
import vn.metech.dto.response.UserResponseAuth;
import vn.metech.exception.PartnerNotFoundException;
import vn.metech.repository.UserServiceJpaRepository;
import vn.metech.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/local")
public class LocalController {

	private IUserService userService;
	private UserServiceJpaRepository userServiceJpaRepository;

	public LocalController(IUserService userService,
	                       UserServiceJpaRepository userServiceJpaRepository    ) {
		this.userService = userService;
		this.userServiceJpaRepository = userServiceJpaRepository;
	}

	@GetMapping("/agent-user-ids")
	public List<String> getAgentUserIds(@RequestParam("user-id") String userId) {
		return userService.getAgentUserIds(userId);
	}

	@GetMapping("/get-tpc-user-ids")
	public List<String> getTPCUserIds() throws PartnerNotFoundException {
		return userService.getTpcUserIds();
	}

	@GetMapping("/get-user-info")
	public UserResponseAuth getUserInformation(@RequestParam("user-id") String userId) {
		return userService.getUserInformation(userId);
	}

	@GetMapping("/get-ref-user-ids")
	public List<String> getRefUserIds(@RequestParam("user-id") String userId) {
		return userService.getRefUserIds(userId);
	}

	@GetMapping("/get-records-user-ids")
	public List<String> getRecordsUserIds(@RequestParam("user-id") String userId) {
		return userService.getRecordsUserIds(userId);
	}

	@GetMapping("/service-access")
	public Boolean canRequest(@RequestParam("user-id") String userId, @RequestParam("code") String code) {

		try {
			return userServiceJpaRepository.findOneByUserIdAndServiceType(userId, code) != null;
		} catch (Exception e) {
			return false;
		}
	}

	@GetMapping("/get-email-admin")
	public List<EmailResponse> getUserEmailAdmin() {
		return userService.getUserEmail();
	}

	@GetMapping("/get-phone-admin")
	public List<PhoneResponse> getUserPhoneAdmin() {
		return userService.getUserPhone();
	}
}
