package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.AppService;
import vn.metech.constant.Transaction;
import vn.metech.constant.TransactionStatus;
import vn.metech.dto.balance.UserBalanceTransactionRequest;
import vn.metech.entity.Balance;
import vn.metech.entity.BalanceTransaction;
import vn.metech.entity.Tariff;
import vn.metech.exception.balance.BalanceNotEnoughException;
import vn.metech.exception.balance.TransactionInvalidException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.*;
import vn.metech.repository.jpa.BalanceCrudRepository;
import vn.metech.repository.jpa.BalanceTransactionCrudRepository;
import vn.metech.repository.jpa.TariffCrudRepository;
import vn.metech.service.IBalanceService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BalanceServiceImpl extends ServiceImpl<Balance> implements IBalanceService {

	private BalanceCrudRepository balanceCrudRepository;
	private BalanceTransactionCrudRepository balanceTransactionCrudRepository;
	private ITariffRepository tariffRepository;
	private IBalanceRepository balanceRepository;
	private IBalanceTransactionRepository balanceTransactionRepository;
	private IBalanceSummaryRepository balanceSummaryRepository;
	private IBalanceSummaryDetailRepository balanceSummaryDetailRepository;
	private TariffCrudRepository tariffCrudRepository;

	public BalanceServiceImpl(
					BalanceCrudRepository balanceCrudRepository,
					BalanceTransactionCrudRepository balanceTransactionCrudRepository,
					ITariffRepository tariffRepository,
					IBalanceRepository balanceRepository,
					IBalanceTransactionRepository balanceTransactionRepository,
					IBalanceSummaryRepository balanceSummaryRepository,
					IBalanceSummaryDetailRepository balanceSummaryDetailRepository,
					TariffCrudRepository tariffCrudRepository) {
		super(balanceRepository);
		this.balanceCrudRepository = balanceCrudRepository;
		this.balanceTransactionCrudRepository = balanceTransactionCrudRepository;
		this.tariffRepository = tariffRepository;
		this.balanceRepository = balanceRepository;
		this.balanceTransactionRepository = balanceTransactionRepository;
		this.balanceSummaryRepository = balanceSummaryRepository;
		this.balanceSummaryDetailRepository = balanceSummaryDetailRepository;
		this.tariffCrudRepository = tariffCrudRepository;
	}

	@Override
	public int calibrateUserBalances() {
		return calibrateUserBalances(new Date());
	}

	@Override
	public int calibrateUserBalances(Date toDate) {
		List<Balance> balances = balanceRepository.getActiveBalancesBy(-1);
		int successCounter = 0;

		for (Balance balance : balances) {
			if (calibrateBalance(balance, toDate))
				successCounter++;
		}

		return successCounter;
	}

	@Override
	public boolean calibrateBalance(Balance balance, Date toDate) {
		BigDecimal amount = balanceTransactionRepository
						.getTotalBalanceTransactionsBy(balance.getId(), balance.getLastCalibration(), toDate);
		balance.setLastCalibration(toDate);
		balance.setLastCalibrationAmount(balance.getLastCalibrationAmount().add(amount));
		balance.setBalance(balance.getLastCalibrationAmount());

		return balanceRepository.update(balance) != null;
	}


	@Override
	public boolean increaseBalanceOf(
					String userId, String referenceId, AppService appService, BigDecimal amount, String note)
					throws BalanceNotEnoughException {
		return saveBalance(userId, referenceId, appService, amount, note, Transaction.TOP_UP);
	}

	@Override
	public boolean paymentTransaction(
					UserBalanceTransactionRequest transactionRequest) throws BalanceNotEnoughException {
//		Tariff tariff = tariffRepository.getTariffBy(transactionRequest.getVerifyService());
		Tariff tariff = tariffCrudRepository.getTariffBy(transactionRequest.getVerifyService());
		BigDecimal amount = BigDecimal.valueOf(10000);
		if (tariff != null) {
			amount = tariff.getPrice();
		}
		Transaction transaction = Transaction.HOLD;
		if (transactionRequest.getTransaction() == Transaction.PAY) transaction = Transaction.PAY;

		return saveBalance(
						transactionRequest.getUserId(),
						transactionRequest.getReferenceId(),
						transactionRequest.getAppService(),
						amount, null, transaction
		);
	}

	@Override
	public boolean releaseTransaction(
					String userId, String requestId, Transaction transaction) throws TransactionInvalidException {
		if (transaction != Transaction.UN_HOLD && transaction != Transaction.REFUND && transaction != Transaction.RELEASE) {
			return false;
		}
		Balance balance = balanceCrudRepository.findByUserId(userId);
		if (balance == null) {
			balance = balanceRepository.create(new Balance(userId));
		}
		BalanceTransaction heldTransaction =
				balanceTransactionRepository.getBalanceTransactionBy(requestId, Transaction.HOLD);

		BalanceTransaction balanceTransaction = new BalanceTransaction(balance, transaction, requestId, BigDecimal.ZERO);
		if (heldTransaction == null) {
			balanceTransaction.setSuccess(false);
			balanceTransaction.setNote("Hold transaction not found '" + requestId + "'");
			balanceTransaction.setStatus(TransactionStatus.ERROR);
			balanceTransactionRepository.create(balanceTransaction);
			throw new TransactionInvalidException(balanceTransaction.getId());
		}
		if (transaction == Transaction.UN_HOLD || transaction == Transaction.REFUND) {
			balanceTransaction.setAmount(heldTransaction.getAmount().multiply(BigDecimal.valueOf(-1)));
		}
		balanceTransaction.setReferenceId(heldTransaction.getId());
		balanceTransaction.setAppService(heldTransaction.getAppService());
		return balanceTransactionRepository.create(balanceTransaction) != null;
	}

	@Override
	public boolean saveBalance(
					String userId, String requestId, AppService appService, BigDecimal amount,
					String note, Transaction transaction) throws BalanceNotEnoughException {
		if (transaction == Transaction.RELEASE || transaction == Transaction.UN_HOLD || transaction == Transaction.REFUND) {
			try {
				return releaseTransaction(userId, requestId, transaction);
			} catch (TransactionInvalidException e) {
				return false;
			}
		}
//		Balance balance = balanceCrudRepository.getBalanceBy(userId);
		Balance balance = balanceCrudRepository.findByUserId(userId);
		if (balance == null) {
			balance = balanceRepository.create(new Balance(userId));
		}
		BalanceTransaction balanceTransaction =
						new BalanceTransaction(balance, transaction, requestId, getAmount(transaction, amount), note);
		balanceTransaction.setAppService(appService);
		BigDecimal newBalance = balance.getBalance().add(balanceTransaction.getAmount());
		if (newBalance.compareTo(BigDecimal.valueOf(0)) < 0) {
			balanceTransaction.setSuccess(false);
			balanceTransaction.setStatus(TransactionStatus.BALANCE_NOT_ENOUGH);
			balanceTransactionRepository.create(balanceTransaction);
			throw new BalanceNotEnoughException();
		}
		if (balanceTransactionRepository.create(balanceTransaction) == null) return false;
		balance.setBalance(newBalance);

		return balanceRepository.update(balance) != null;
	}

	private BigDecimal getAmount(Transaction transaction, BigDecimal amount) {
		if (transaction == Transaction.PAY || transaction == Transaction.HOLD) {
			return amount.multiply(BigDecimal.valueOf(-1));
		}

		if (transaction == Transaction.RELEASE) {
			return BigDecimal.ZERO;
		}

		return amount;
	}
}
