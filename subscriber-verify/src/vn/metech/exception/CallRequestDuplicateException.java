package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class CallRequestDuplicateException extends BaseException {

	public CallRequestDuplicateException() {
		this("[requestId] exists in system");
	}

	public CallRequestDuplicateException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.CallReference.REQUEST_DUPLICATE;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
