package vn.metech.repository;

import vn.metech.constant.Transaction;
import vn.metech.entity.BalanceTransaction;
import vn.metech.jpa.repository.IRepository;

import java.math.BigDecimal;
import java.util.Date;

public interface IBalanceTransactionRepository extends IRepository<BalanceTransaction> {

	BigDecimal getTotalBalanceTransactionsBy(String balanceId, Date from, Date to, Transaction... trans);

	BalanceTransaction getBalanceTransactionBy(String requestId, Transaction transaction);
}
