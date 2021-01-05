package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class IdRequestDuplicateException extends BaseException {

	public IdRequestDuplicateException() {
		this("[requestId] exists in system");
	}

	public IdRequestDuplicateException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.IdReference.REQUEST_DUPLICATE;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
