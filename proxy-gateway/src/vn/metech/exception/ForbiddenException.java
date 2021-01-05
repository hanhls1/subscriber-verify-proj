package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class ForbiddenException extends BaseException {

	public ForbiddenException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.Auth.FORBIDDEN;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.FORBIDDEN;
	}

}
