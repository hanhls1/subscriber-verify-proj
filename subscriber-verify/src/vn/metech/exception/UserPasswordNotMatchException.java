package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class UserPasswordNotMatchException extends BaseException {

	public UserPasswordNotMatchException() {
		super("Password not match");
	}

	public UserPasswordNotMatchException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.User.PASSWORD_NOT_MATCH;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
