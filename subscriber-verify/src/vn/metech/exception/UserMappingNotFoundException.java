package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.shared.BaseException;

public class UserMappingNotFoundException extends BaseException {

	public UserMappingNotFoundException(String id) {
		super("UserMapping '" + id + "' not found");
	}


	@Override
	public String getStatusCode() {
		return "USER_MAPPING_NOT_FOUND";
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
