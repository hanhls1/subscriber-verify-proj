package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.dto.MtTpcConfirmFilter;
import vn.metech.entity.TpcConfirm;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ITpcConfirmRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class TPCConfirmRepositoryImpl extends RepositoryImpl<TpcConfirm> implements ITpcConfirmRepository {

	public TPCConfirmRepositoryImpl(EntityManager entityManager) {
		super(entityManager, TpcConfirm.class);
	}

	@Override
	public TpcConfirm getConfirmBy(String phoneNumber, Date checkedDate, Date duplicateBefore) {
		// @formatter:off
		try {
			String jpql = "SELECT c " +
										"FROM " + clazz.getName() + " c " +
										"WHERE c.deleted = :deleted " +
											"AND c.phoneNumber = :phoneNumber " +
											"AND (c.checkedDate between :duplicateBefore and :checkedDate) ";
		// @formatter:on
			return entityManager.createQuery(jpql, clazz)
							.setParameter("deleted", false).setParameter("phoneNumber", phoneNumber)
							.setParameter("checkedDate", checkedDate).setParameter("duplicateBefore", duplicateBefore)
							.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public PagedResult<TpcConfirm> getResponsesBy(MtTpcConfirmFilter filter, List<String> userIds) {
		if (userIds == null || userIds.isEmpty()) {
			return new PagedResult<>();
		}
		boolean phoneNumberFill = !StringUtils.isEmpty(filter.getPhoneNumber());
		boolean fromDateFill = filter.getFromDate() != null;
		boolean toDateFill = filter.getToDate() != null;
		// @formatter:off
		String countJpql =
						"SELECT COUNT(id) " +
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND createdBy IN (:userIds) " +
							(phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
							(fromDateFill ? "AND :fromDate <= createdDate " : "") +
							(toDateFill ? "AND :toDate >= createdDate " : "");

		String selectJpql =
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND createdBy IN (:userIds) " +
							(phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
							(fromDateFill ? "AND :fromDate <= createdDate " : "") +
							(toDateFill ? "AND :toDate >= createdDate " : "") +
						"ORDER BY checkedDate DESC ";
		// @formatter:on
		try {
			TypedQuery<Long> countQuery =
							entityManager
											.createQuery(countJpql, Long.class)
											.setParameter("deleted", false)
											.setParameter("userIds", userIds);

			TypedQuery<TpcConfirm> selectQuery = entityManager
							.createQuery(selectJpql, clazz)
							.setParameter("deleted", false)
							.setParameter("userIds", userIds)
							.setFirstResult(filter.skip())
							.setMaxResults(filter.getPageSize());

			if (phoneNumberFill) {
				selectQuery.setParameter("phoneNumber", "%" + filter.getPhoneNumber() + "%");
				countQuery.setParameter("phoneNumber", "%" + filter.getPhoneNumber() + "%");
			}
			if (fromDateFill) {
				selectQuery.setParameter("fromDate", filter.getFromDate());
				countQuery.setParameter("fromDate", filter.getFromDate());
			}
			if (toDateFill) {
				selectQuery.setParameter("toDate", filter.getToDate());
				countQuery.setParameter("toDate", filter.getToDate());
			}

			return new PagedResult<>(
							countQuery.getSingleResult().intValue(),
							selectQuery.getResultList()
			);
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}
}
