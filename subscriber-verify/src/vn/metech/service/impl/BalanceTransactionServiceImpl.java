package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.entity.BalanceTransaction;
import vn.metech.repository.IBalanceTransactionRepository;
import vn.metech.service.IBalanceTransactionService;

@Service
@Transactional
public class BalanceTransactionServiceImpl implements IBalanceTransactionService {

	private IBalanceTransactionRepository balanceTransactionRepository;

	public BalanceTransactionServiceImpl(
					IBalanceTransactionRepository balanceTransactionRepository) {
		this.balanceTransactionRepository = balanceTransactionRepository;
	}

//	@Override
//	public BalanceTransaction getBalanceTransactionBy(String requestId, Transaction transaction) {
//		return balanceTransactionRepository.getBalanceTransactionBy(requestId, transaction);
//	}

	@Override
	public BalanceTransaction update(BalanceTransaction balanceTransaction) {
		return balanceTransactionRepository.update(balanceTransaction);
	}
}
