package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class AdRequestDuplicateException extends BaseException {

	public AdRequestDuplicateException() {
		this("[requestId] exists in system");
	}

	public AdRequestDuplicateException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.AdReference.REQUEST_DUPLICATE;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
