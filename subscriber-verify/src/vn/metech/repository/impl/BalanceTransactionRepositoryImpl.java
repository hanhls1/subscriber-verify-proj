package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.Transaction;
import vn.metech.entity.BalanceTransaction;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IBalanceTransactionRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Repository
public class BalanceTransactionRepositoryImpl
				extends RepositoryImpl<BalanceTransaction> implements IBalanceTransactionRepository {

	public BalanceTransactionRepositoryImpl(EntityManager entityManager) {
		super(entityManager, BalanceTransaction.class);
	}

	@Override
	public BigDecimal getTotalBalanceTransactionsBy(
					String balanceId, Date sumFrom, Date sumTo, Transaction... transactions) {

		boolean toFill = sumTo != null;
		boolean fromFill = sumFrom != null;
		boolean transFill = transactions != null && transactions.length > 0;

		// @formatter:off
		String jpql =
						"SELECT SUM(amount) " +
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND balanceId = :balanceId " +
							"AND success = :success " +
							(toFill ? "AND createdDate <= :toDate " : "") +
							(fromFill ? "AND createdDate >= :fromDate " : "") +
							(transFill ? "AND transaction IN (:transactions)" : "");
		// @formatter:on
		try {
			TypedQuery<BigDecimal> sumQuery = entityManager
							.createQuery(jpql, BigDecimal.class)
							.setParameter("deleted", false)
							.setParameter("success", true)
							.setParameter("balanceId", balanceId);

			if (fromFill) {
				sumQuery.setParameter("fromDate", sumFrom);
			}
			if (toFill) {
				sumQuery.setParameter("toDate", sumTo);
			}
			if (transFill) {
				sumQuery.setParameter("transactions", Arrays.asList(transactions));
			}
			BigDecimal result = sumQuery.getSingleResult();

			return result == null ? BigDecimal.ZERO : result;
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	@Override
	public BalanceTransaction getBalanceTransactionBy(String requestId, Transaction transaction) {
		// @formatter:off
		String jpql =
				"FROM " + clazz.getName() + " " +
						"WHERE requestId = :requestId " +
						"AND transaction = :transaction ";
		// @formatter:on
		try {
			return entityManager
					.createQuery(jpql, clazz)
					.setParameter("requestId", requestId)
					.setParameter("transaction", transaction)
					.setMaxResults(1)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
