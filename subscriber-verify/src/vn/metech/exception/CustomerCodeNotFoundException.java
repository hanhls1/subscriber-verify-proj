package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class CustomerCodeNotFoundException extends BaseException {

	public CustomerCodeNotFoundException() {
		this("[customerCode] not found");
	}

	public CustomerCodeNotFoundException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.LocationVerify.CUSTOMER_CODE_NOT_FOUND;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
