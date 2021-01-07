package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ITpcConfirmInfoRepository;

import javax.persistence.EntityManager;

@Repository
public class TPCConfirmInfoRepositoryImpl extends RepositoryImpl<TpcConfirmInfo> implements ITpcConfirmInfoRepository {

	public TPCConfirmInfoRepositoryImpl(EntityManager entityManager) {
		super(entityManager, TpcConfirmInfo.class);
	}

	@Override
	public TpcConfirmInfo getConfirmInfoBy(String requestId, String tpcConfirmId) {
		try {
			// @formatter:off
			String jpql = "FROM " + clazz.getName() + " " +
										"WHERE deleted = :deleted " +
											"AND requestId = :requestId " +
											"AND confirmId = :tpcConfirmId ";
		// @formatter:on
			return entityManager.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("requestId", requestId)
							.setParameter("tpcConfirmId", tpcConfirmId)
							.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
