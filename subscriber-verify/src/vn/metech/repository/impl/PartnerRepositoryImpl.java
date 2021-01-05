package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.dto.request.PartnerFilterRequest;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.entity.Partner;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IPartnerRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class PartnerRepositoryImpl extends RepositoryImpl<Partner> implements IPartnerRepository {

	public PartnerRepositoryImpl(EntityManager entityManager) {
		super(entityManager, Partner.class);
	}


	@Override
	public List<Partner> fillPartnersBy(PartnerFilterRequest partnerRequest) {
		boolean nameFill = !StringUtils.isEmpty(partnerRequest.getKeyword());
		// @formatter:off
		String jpql = "FROM " + clazz.getName() + " " +
									"WHERE deleted = :deleted " +
									(nameFill ? "AND (name LIKE :keyword OR partnerCode LIKE :keyword) " : "");
		// @formatter:on
		try {
			TypedQuery<Partner> partnerQuery =
							entityManager.createQuery(jpql, clazz).setParameter("deleted", false);
			if (nameFill) {
				partnerQuery.setParameter("keyword", "%" + partnerRequest.getKeyword() + "%");
			}
			return partnerQuery.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

//	@Override
//	public Partner getPartnerById(String partnerId) {
//		// @formatter:off
//		if (StringUtils.isEmpty(partnerId)) {	return null; }
//		try {
//			String jpql = "FROM " + clazz.getName() + " " +
//										"WHERE deleted = :deleted " +
//											"AND id = :partnerId ";
//			// @formatter:on
//
//			return entityManager.createQuery(jpql, clazz).setParameter("deleted", false)
//							.setParameter("partnerId", partnerId).setMaxResults(1).getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}

	@Override
	public PagedResult<Partner> getPartnersWithPaging(PartnerPageRequest partnerPageRequest) {
		boolean nameFill = !StringUtils.isEmpty(partnerPageRequest.getKeyword());
		// @formatter:off
		String jpql = "FROM " + clazz.getName() + " " +
									"WHERE deleted = :deleted " +
									(nameFill ? "AND (name LIKE :keyword OR partnerCode LIKE :keyword) " : "");
		// @formatter:on
		try {
			TypedQuery<Long> countPartnerQuery =
							entityManager.createQuery("SELECT COUNT(*) " + jpql, Long.class).setParameter("deleted", false);
			TypedQuery<Partner> partnerQuery =
							entityManager.createQuery(jpql, clazz).setParameter("deleted", false);
			if (nameFill) {
				partnerQuery.setParameter("keyword", "%" + partnerPageRequest.getKeyword() + "%");
				countPartnerQuery.setParameter("keyword", "%" + partnerPageRequest.getKeyword() + "%");
			}
			Long counter = countPartnerQuery.getSingleResult();
			if (counter == 0) {
				return new PagedResult<>();
			}
			return new PagedResult<>(
							counter.intValue(),
							partnerQuery.setFirstResult(partnerPageRequest.skip())
											.setMaxResults(partnerPageRequest.take()).getResultList()
			);
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}
}
