package vn.metech.exception.aio;


import vn.metech.common.StatusCode;

public abstract class BaseException extends Exception {

	public BaseException(String msg) {
		super(msg);
	}

	public abstract StatusCode getStatusCode();

}
