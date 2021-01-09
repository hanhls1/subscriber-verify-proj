package vn.metech.shared;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;

public abstract class BaseException extends Exception {

	public BaseException(String msg) {
		super(msg);
	}

	public abstract String getStatusCode();

	public abstract HttpStatus getHttpStatusCode();

}
