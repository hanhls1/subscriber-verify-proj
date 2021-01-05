package vn.metech.service;

import vn.metech.constant.AppService;
import vn.metech.constant.Transaction;
import vn.metech.dto.balance.UserBalanceTransactionRequest;
import vn.metech.entity.Balance;
import vn.metech.exception.balance.BalanceNotEnoughException;
import vn.metech.exception.balance.TransactionInvalidException;
import vn.metech.jpa.service.IService;

import java.math.BigDecimal;
import java.util.Date;

public interface IBalanceService extends IService<Balance> {

	int calibrateUserBalances();

	int calibrateUserBalances(Date toDate);

	boolean calibrateBalance(Balance balance, Date toDate);

//	BigDecimal getBalanceBy(String userId);

//	boolean decreaseBalanceOf(String userId, String referenceId, AppService appService,
//	                          BigDecimal amount, String note) throws BalanceNotEnoughException;

	boolean increaseBalanceOf(String userId, String referenceId, AppService appService,
	                          BigDecimal amount, String note) throws BalanceNotEnoughException;

	boolean paymentTransaction(UserBalanceTransactionRequest transactionRequest) throws BalanceNotEnoughException;

	boolean releaseTransaction(String userId, String requestId, Transaction transaction) throws TransactionInvalidException;

	boolean saveBalance(
					String userId, String requestId, AppService appService,
					BigDecimal amount, String note, Transaction transaction) throws BalanceNotEnoughException;
}
