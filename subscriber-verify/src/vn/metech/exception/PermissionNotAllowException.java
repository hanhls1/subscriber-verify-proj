package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.shared.BaseException;

public class PermissionNotAllowException extends BaseException {

	public PermissionNotAllowException(String user) {
		super("user '" + user + "' not allow");
	}

	@Override
	public String getStatusCode() {
		return "PERMISSION_NOT_ALLOW";
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
