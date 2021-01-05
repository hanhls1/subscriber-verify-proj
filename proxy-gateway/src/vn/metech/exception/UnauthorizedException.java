package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class UnauthorizedException extends BaseException {

	public UnauthorizedException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.Auth.UN_AUTHORIZED;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.UNAUTHORIZED;
	}
}
