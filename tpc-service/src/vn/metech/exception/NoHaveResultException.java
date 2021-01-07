package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class NoHaveResultException extends BaseException {

	public NoHaveResultException(String requestId) {
		super("Request '" + requestId + "' no result yet");
	}

	@Override
	public String getStatusCode() {
		return StatusCode.Kyc02.NO_RESULTS_YET;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.NO_CONTENT;
	}

}
