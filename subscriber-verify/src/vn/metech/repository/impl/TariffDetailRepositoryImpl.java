package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.TariffDetail;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ITariffDetailRepository;

import javax.persistence.EntityManager;

@Repository
public class TariffDetailRepositoryImpl
				extends RepositoryImpl<TariffDetail> implements ITariffDetailRepository {

	public TariffDetailRepositoryImpl(EntityManager entityManager) {
		super(entityManager, TariffDetail.class);
	}

//	@Override
//	public void deleteTariffDetailsBy(String tariffId) {
//		try {
//			entityManager
//							.createQuery("delete from " + clazz.getName() + " where tariffId = :tariffId ")
//							.setParameter("tariffId", tariffId);
//		} catch (Exception ignored) {
//		}
//	}
}
