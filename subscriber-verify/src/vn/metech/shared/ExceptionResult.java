package vn.metech.shared;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;

public class ExceptionResult extends ActionResult {

	public ExceptionResult(Throwable ex) {
		super(StatusCode.ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	public ExceptionResult(Exception ex) {
		super(StatusCode.ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	public ExceptionResult(BaseException ex) {
		super(ex.getStatusCode(), ex.getHttpStatusCode(), ex.getMessage());
	}

}
