package vn.metech.shared;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;

public class InternalErrorException extends BaseException {

	public InternalErrorException() {
		this("Have an error when process!");
	}

	public InternalErrorException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.ERROR;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
