package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class LocationRequestDuplicateException extends BaseException {

	public LocationRequestDuplicateException() {
		this("[requestId] exists in system");
	}

	public LocationRequestDuplicateException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.LocationVerify.REQUEST_DUPLICATE;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
