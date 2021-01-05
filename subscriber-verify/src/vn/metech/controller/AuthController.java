package vn.metech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.metech.dto.request.UserLoginRequest;
import vn.metech.dto.response.UserLoginResponse;
import vn.metech.exception.UnauthorizedException;
import vn.metech.exception.UserInvalidException;
import vn.metech.exception.UserNotFoundException;
import vn.metech.service.IAuthService;
import vn.metech.service.IUserService;
import vn.metech.shared.ActionResult;
import vn.metech.shared.AuthorizeRequest;

import javax.validation.Valid;

import static vn.metech.constant.Auth.AUTH_CHECK_PATH;
import static vn.metech.constant.Auth.AUTH_CONTROLLER;

@RestController
@RequestMapping(AUTH_CONTROLLER)
public class AuthController {

	private IAuthService authService;

	public AuthController(
					IAuthService authService,
					IUserService userService) {
		this.authService = authService;
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping({"/generate-token", "/login", "/sign-in"})
	public ActionResult login(@Valid @RequestBody UserLoginRequest loginRequest)
					throws UserInvalidException {

		UserLoginResponse userLoginResponse = authService
						.login(
										loginRequest.getUsername(),
										loginRequest.getPassword(),
										loginRequest.getPartnerCode()
						);

		return new ActionResult(userLoginResponse);
	}

	@PostMapping({AUTH_CHECK_PATH})
	public ActionResult canAccess(@RequestBody AuthorizeRequest authRequest) throws UnauthorizedException, UserNotFoundException {
		return new ActionResult(authService.isAccepted(authRequest));
	}


}