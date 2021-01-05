package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.shared.BaseException;

public class UserMappingDuplicateException extends BaseException {

	public UserMappingDuplicateException() {
		super("UserMapping is exists");
	}

	@Override
	public String getStatusCode() {
		return "USER_MAPPING_DUPLICATE";
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
