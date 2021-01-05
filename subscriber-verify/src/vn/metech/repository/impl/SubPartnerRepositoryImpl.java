package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.SubPartnerFilterRequest;
import vn.metech.entity.SubPartner;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ISubPartnerRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;
import vn.metech.dto.response.SubPartnerResponse;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class SubPartnerRepositoryImpl extends RepositoryImpl<SubPartner> implements ISubPartnerRepository {

	public SubPartnerRepositoryImpl(EntityManager entityManager) {
		super(entityManager, SubPartner.class);
	}

//	@Override
//	public SubPartner getSubPartnerById(String subPartnerId) {
//		try {
//			String jpql = " FROM " + clazz.getName() + " WHERE deleted = :deleted" +
//							" AND id = :subPartnerId";
//			// @formatter:on
//			return entityManager.createQuery(jpql, clazz).setParameter("deleted", false)
//							.setParameter("subPartnerId", subPartnerId).setMaxResults(1).getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}

//	@Override
//	public List<SubPartner> getSubPartnersBy(String partnerId) {
//		try {
//			// @formatter:off
//			String jpql =
//							" FROM " + clazz.getName() +
//							" WHERE deleted = :deleted" +
//								" AND partnerId = :partnerId";
//			// @formatter:on
//			return entityManager
//							.createQuery(jpql, clazz)
//							.setParameter("deleted", false)
//							.setParameter("partnerId", partnerId)
//							.getResultList();
//		} catch (Exception e) {
//			return Collections.emptyList();
//		}
//	}

	@Override
	public List<SubPartner> getSubPartnersBy(SubPartnerFilterRequest subPartnerFilterRequest) {
		boolean nameFill = !StringUtils.isEmpty(subPartnerFilterRequest.getKeyword());
		try {
			// @formatter:off
			String jpql =
							" FROM " + clazz.getName() +
							" WHERE deleted = :deleted" +
								" AND partnerId = :partnerId" +
								(nameFill ? " AND (name LIKE :keyword OR customerCode LIKE :keyword)" : "");
			// @formatter:on
			TypedQuery<SubPartner> subPartnerQuery =
							entityManager.createQuery(jpql, clazz)
											.setParameter("deleted", false)
											.setParameter("partnerId", subPartnerFilterRequest.getPartnerId());
			if (nameFill) {
				subPartnerQuery.setParameter("keyword", "%" + subPartnerFilterRequest.getKeyword() + "%");
			}
			return subPartnerQuery.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public PagedResult<SubPartner> getSubPartnersWithPaging(PartnerPageRequest partnerPageRequest) {
		boolean nameFill = !StringUtils.isEmpty(partnerPageRequest.getKeyword());
		// @formatter:off
		String jpql = "FROM " + clazz.getName() + " " +
									"WHERE deleted = :deleted " +
									(nameFill ? "AND (name LIKE :keyword OR partnerCode LIKE :keyword) " : "");
		// @formatter:on
		try {
			TypedQuery<Long> countPartnerQuery =
							entityManager.createQuery("SELECT COUNT(*) " + jpql, Long.class).setParameter("deleted", false);
			TypedQuery<SubPartner> partnerQuery =
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
	public List<SubPartnerResponse> getConfirmInfoSubPartnerIdBy(String partnerId){
		boolean partnerFill = !StringUtils.isEmpty(partnerId);

		String selectQl = "select new vn.metech.dto.response.SubPartnerResponse( su.id, su.name)" +
				" from SubPartner su where su.id is not null " +
				(partnerFill ? " and su.partnerId = :partnerId " : "") +
				" group by su.id, su.name ORDER BY su.name";
		TypedQuery<SubPartnerResponse> selectQuery = entityManager.createQuery(selectQl, SubPartnerResponse.class);

		if (partnerFill) {
			selectQuery.setParameter("partnerId", partnerId);
		}

		return selectQuery.getResultList();
	}
}
