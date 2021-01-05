package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class PartnerNotFoundException extends BaseException {

	public PartnerNotFoundException() {
		super("Partner not found");
	}

	public PartnerNotFoundException(String msg) {
		super(msg);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.Partner.PARTNER_NOT_FOUND;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
