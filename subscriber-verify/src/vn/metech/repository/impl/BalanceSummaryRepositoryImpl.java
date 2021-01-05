package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.BalanceSummary;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IBalanceSummaryRepository;

import javax.persistence.EntityManager;

@Repository
public class BalanceSummaryRepositoryImpl extends RepositoryImpl<BalanceSummary> implements IBalanceSummaryRepository {

	public BalanceSummaryRepositoryImpl(EntityManager entityManager) {
		super(entityManager, BalanceSummary.class);
	}

//	@Override
//	public BalanceSummary getBalanceSummaryIncludeDetailsByUser(String userId) {
//		try {
//			String jpql = "SELECT bs FROM " + clazz.getName() + " bs LEFT JOIN FETCH bs.balanceSummaryDetails " +
//							"WHERE bs.deleted = :deleted AND bs.userId = :userId ";
//			return entityManager.createQuery(jpql, clazz)
//							.setParameter("deleted", false).setParameter("userId", userId).setMaxResults(1).getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}

}
