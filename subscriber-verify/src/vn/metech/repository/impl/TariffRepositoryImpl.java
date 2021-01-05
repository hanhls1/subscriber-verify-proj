package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.dto.tariff.TariffFilterPageRequest;
import vn.metech.entity.Tariff;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ITariffRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TariffRepositoryImpl
				extends RepositoryImpl<Tariff> implements ITariffRepository {

	public TariffRepositoryImpl(EntityManager entityManager) {
		super(entityManager, Tariff.class);
	}

//	@Override
//	public Tariff getTariffBy(AppService... appServices) {
//		if (appServices == null || appServices.length == 0) return null;
//		// @formatter:off
//		String jpql =
//						"SELECT tr " +
//						"FROM " + clazz.getName() + " tr " +
//						"WHERE tr.appService IN (:appServices)";
//		// @formatter:on
//		try {
//			return entityManager
//							.createQuery(jpql, clazz)
//							.setParameter("appServices", Arrays.asList(appServices))
//							.setMaxResults(1)
//							.getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public Tariff getTariffBy(VerifyService... verifyServices) {
//		if (verifyServices == null || verifyServices.length == 0) return null;
//		// @formatter:off
//		String jpql = "FROM " + clazz.getName() + " " +
//									"WHERE deleted = :deleted " +
//										"AND verifyService IN (:verifyServices) " +
//									"ORDER BY createdDate ";
//
//		// @formatter:on
//		try {
//			return entityManager
//							.createQuery(jpql, clazz)
//							.setParameter("verifyServices", Arrays.asList(verifyServices))
//							.setParameter("deleted", false)
//							.setMaxResults(1)
//							.getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}

	@Override
	public PagedResult<Tariff> findTariffWithPaging(TariffFilterPageRequest tariffRequest) {
		try {
			boolean keywordFill = !StringUtils.isEmpty(tariffRequest.getKeyword());
			TypedQuery<Long> countQuery = entityManager.createQuery(
							"select count (id) from " + clazz.getName() + " where deleted = :deleted " +
											(keywordFill ? " and(appServiceName like :keyword or verifyServiceName like :keyword)" : ""),
							Long.class);
			TypedQuery<Tariff> selectQuery = entityManager.createQuery(
							"from " + clazz.getName() + " where deleted = :deleted " +
											(keywordFill ? " and (appServiceName like :keyword or verifyServiceName like :keyword)" : ""),
							clazz);

			countQuery.setParameter("deleted", false);
			selectQuery.setParameter("deleted", false);
			if (keywordFill) {
				countQuery.setParameter("keyword", "%" + tariffRequest.getKeyword().toUpperCase() + "%");
				selectQuery.setParameter("keyword", "%" + tariffRequest.getKeyword().toUpperCase() + "%");
			}
			Long counter = countQuery.getSingleResult();
			if (counter != null && counter > 0) {
				List<Tariff> tariffs = selectQuery
								.setFirstResult(tariffRequest.skip()).setMaxResults(tariffRequest.take()).getResultList();
				return new PagedResult<>(counter.intValue(), tariffs);
			}
		} catch (Exception ignored) {
		}
		return new PagedResult<>();
	}

}
