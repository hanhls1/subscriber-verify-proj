package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class UserInvalidException extends BaseException {

	public UserInvalidException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.User.USER_INVALID;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.FORBIDDEN;
	}

}
