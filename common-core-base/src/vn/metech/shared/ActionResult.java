package vn.metech.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActionResult {

	private String statusCode;
	private int httpStatusCode;
	private Object result;

	public ActionResult() {
		statusCode = StatusCode.SUCCESS;
		httpStatusCode = HttpStatus.OK.value();
	}

	public ActionResult(Object result) {
		this();
		this.result = result;
	}

	public ActionResult(String statusCode, HttpStatus httpStatusCode, Object result) {
		this(result);
		this.statusCode = statusCode;
		this.httpStatusCode = httpStatusCode.value();
	}

	public ActionResult(String statusCode, int httpStatusCode, Object result) {
		this(result);
		this.statusCode = statusCode;
		this.httpStatusCode = httpStatusCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
