package vn.metech.dto.response;


import vn.metech.exception.aio.BaseException;

public class ExceptionResult extends ActionResult {

	public ExceptionResult(Throwable ex) {

	}

	public ExceptionResult(Exception ex) {
	}

	public ExceptionResult(BaseException ex) {
	}

}
