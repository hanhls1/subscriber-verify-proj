package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.dto.request.UserMappingCreateRequest;
import vn.metech.dto.request.UserMappingUpdateRequest;
import vn.metech.exception.UserMappingDuplicateException;
import vn.metech.exception.UserMappingNotFoundException;
import vn.metech.exception.UserNotFoundException;
import vn.metech.repository.jpa.UserMappingCrudRepository;
import vn.metech.service.IUserMappingService;
import vn.metech.shared.ActionResult;

@RestController
@RequestMapping("/user/mapping")
public class UserMappingController {

	private IUserMappingService userMappingService;
	private UserMappingCrudRepository userMappingCrudRepository;

	public UserMappingController(IUserMappingService userMappingService,
								 UserMappingCrudRepository userMappingCrudRepository) {
		this.userMappingService = userMappingService;
		this.userMappingCrudRepository = userMappingCrudRepository;
	}

	@GetMapping("/find")
	public ActionResult findUserMapping(
					@RequestParam("q") String keyword,
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr) {
		return new ActionResult(userMappingService.findUserWithMappings(keyword, userId, remoteAddr));
	}

	@PostMapping("/create")
	public ActionResult createUserMapping(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody UserMappingCreateRequest userMappingCreateRequest)
					throws UserNotFoundException, UserMappingDuplicateException {
		return new ActionResult(userMappingService.createUserMapping(userMappingCreateRequest, userId));
	}

	@PostMapping("/update")
	public ActionResult updateUserMapping(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestBody UserMappingUpdateRequest userMappingUpdateRequest)
					throws UserNotFoundException, UserMappingNotFoundException, UserMappingDuplicateException {
		return new ActionResult(userMappingService.updateUserMapping(userMappingUpdateRequest, userId));
	}

	@PostMapping("/delete")
	public ActionResult updateUserMapping(
					@RequestParam("id") String mappingId,
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr)
					throws UserMappingNotFoundException {
		return new ActionResult(userMappingService.deleteUserMapping(mappingId, userId));
	}

}
