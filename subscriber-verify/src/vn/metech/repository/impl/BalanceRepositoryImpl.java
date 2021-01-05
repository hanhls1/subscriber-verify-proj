package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.Balance;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IBalanceRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class BalanceRepositoryImpl
				extends RepositoryImpl<Balance> implements IBalanceRepository {

	public BalanceRepositoryImpl(EntityManager entityManager) {
		super(entityManager, Balance.class);
	}

	@Override
	public List<Balance> getActiveBalancesBy(int limit) {
		// @formatter:off
		String jpql =
						"SELECT b " +
						"FROM " + clazz.getName() + " b " +
						"WHERE b.deleted = :deleted ";
		// @formatter:on
		try {
			TypedQuery<Balance> query = entityManager.createQuery(jpql, clazz)
							.setParameter("deleted", false);

			if (limit > 0) {
				query.setMaxResults(limit);
			}

			return query.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}
