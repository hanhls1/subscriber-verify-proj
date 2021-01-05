package vn.metech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.constant.StatusCode;
import vn.metech.dto.request.*;
import vn.metech.dto.response.UserCreateResponse;
import vn.metech.dto.response.UserRoleResponse;
import vn.metech.exception.*;
import vn.metech.service.IRedisService;
import vn.metech.service.IUserService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/user")
public class UserController {

	private IUserService userService;
	private IRedisService redisService;
	private ExecutorService executorService;

	public UserController(IUserService userService, IRedisService redisService) {
		this.userService = userService;
		this.redisService = redisService;
		this.executorService = Executors.newFixedThreadPool(1);
	}

	@PostMapping("/change-password")
	public ActionResult changePassword(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody UserChangePasswordRequest request) throws UserPasswordNotMatchException {
		ActionResult actionResult = new ActionResult();

		if (userService.changePassword(request, userId, remoteAddr) == null) {
			actionResult.setStatusCode(StatusCode.ERROR);
			actionResult.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} else {
			actionResult.setResult("password changed");
		}

		return actionResult;
	}

	@PostMapping("/create")
	public ActionResult createUser(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody UserCreateRequest userCreateRequest)
					throws PartnerNotFoundException, UserPasswordNotMatchException, UserExitingException {

		UserCreateResponse userCreateResponse = userService.createUser(userCreateRequest, userId);
		executorService.submit(() -> redisService.refresh());

		return new ActionResult();
	}

	@PostMapping("/update-roles")
	public ActionResult updateRoles(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody UserRoleUpdateRequest userRoleUpdateRequest) throws UserInvalidException {
		UserRoleResponse userRoleResponse = userService.updateRole(userRoleUpdateRequest.getUserId(),
						userRoleUpdateRequest.getRoleIds(), userId);
		executorService.submit(() -> redisService.refresh());
		return new ActionResult(userRoleResponse);
	}

	@PostMapping("/get-updatable-users")
	public ActionResult getUpdatableUsers(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody UserFilterPagedRequest userFilterPagedRequest) {
		return new ActionResult(userService.getUpdatableUsers(userFilterPagedRequest, userId));
	}

	@PostMapping("/get-access-paths")
	public ActionResult getUserAccessPaths(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody UserInfoRequest userInfoRequest) {
		return new ActionResult(userService.getAccessPaths(userInfoRequest));
	}

	@GetMapping("/user-details")
	public ActionResult getUserDetails(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@RequestParam("user-id") String userDetailId) throws UserNotFoundException {
		return new ActionResult(userService.getUserDetails(userDetailId, userId));
	}

	@PostMapping("/get-all-users")
	public ActionResult getAllUsers(@RequestHeader(Auth.USER_KEY) String userId,
	                                @RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
	                                @Valid @RequestBody UserFilterPagedRequest userFilterPagedRequest) {
		return new ActionResult(userService.getListUsersBy(userFilterPagedRequest, userId));
	}

	@PostMapping("/update")
	public ActionResult updateUser(@RequestHeader(Auth.USER_KEY) String userId,
	                               @RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
	                               @Valid @RequestBody UserUpdateRequest userUpdateRequest)
					throws UserNotFoundException, PartnerNotFoundException {
		return new ActionResult(userService.updateUser(userUpdateRequest, userId));
	}
}
