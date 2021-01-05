package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class UserExitingException extends BaseException {

	public UserExitingException(String email) {
		super("Email '" + email + "' existing");
	}

	@Override
	public String getStatusCode() {
		return StatusCode.User.USER_EXISTING;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
