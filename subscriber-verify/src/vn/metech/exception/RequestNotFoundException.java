package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.shared.BaseException;

public class RequestNotFoundException extends BaseException {

	public RequestNotFoundException(String customerRequestId) {
		super("Request '" + customerRequestId + "' not found");
	}

	@Override
	public String getStatusCode() {
		return "REQUEST_NOT_FOUND";
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
