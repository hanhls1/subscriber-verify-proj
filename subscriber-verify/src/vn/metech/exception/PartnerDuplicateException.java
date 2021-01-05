package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.shared.BaseException;

public class PartnerDuplicateException extends BaseException {

	public PartnerDuplicateException(String partner) {
		super("Partner '" + partner + "' duplicate");
	}

	@Override
	public String getStatusCode() {
		return "PARTNER_DUPLICATE";
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
