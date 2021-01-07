package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class BalanceNotEnoughException extends BaseException {

	public BalanceNotEnoughException() {
		super("Balance not enough");
	}

	@Override
	public String getStatusCode() {
		return StatusCode.Balance.BALANCE_NOT_ENOUGH;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.NO_CONTENT;
	}

}
