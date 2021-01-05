package vn.metech.exception.balance;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class BalanceNotFoundException extends BaseException {

	public BalanceNotFoundException(String userId) {
		super("Cannot found balance of user: " + userId);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.Balance.BALANCE_NOT_FOUND;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
