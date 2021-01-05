package vn.metech.exception.tariff;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class TariffNotFoundException extends BaseException {

	public TariffNotFoundException(String tariffId) {
		super("Tariff '" + tariffId + "' not found");
	}

	@Override
	public String getStatusCode() {
		return StatusCode.Tariff.TARIFF_NOT_FOUND;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
