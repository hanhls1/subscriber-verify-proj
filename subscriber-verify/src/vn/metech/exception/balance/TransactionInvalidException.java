package vn.metech.exception.balance;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class TransactionInvalidException extends BaseException {

	public TransactionInvalidException(String transactionId) {
		super("Transaction invalid '" + transactionId + "'");
	}

	@Override
	public String getStatusCode() {
		return StatusCode.BalanceTransaction.TRANSACTION_INVALID;
	}

	@Override
	public HttpStatus getHttpStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
