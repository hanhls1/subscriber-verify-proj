package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestType;
import vn.metech.dto.MtCallFilter;
import vn.metech.entity.CallResponse;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ICallResponseRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CallResponseRepositoryImpl
				extends RepositoryImpl<CallResponse> implements ICallResponseRepository {

	public CallResponseRepositoryImpl(EntityManager entityManager) {
		super(entityManager, CallResponse.class);
	}


	@Override
	public PagedResult<CallResponse> getResponsesBy(MtCallFilter toFilter, RequestType requestType, List<String> userIds) {
		boolean phoneNumberFill = !StringUtils.isEmpty(toFilter.getPhoneNumber());
		boolean fromDateFill = toFilter.getFromDate() != null;
		boolean toDateFill = toFilter.getToDate() != null;

		// @formatter:off
		String countJpql =
						"SELECT COUNT(id) " +
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND createdBy IN (:userIds) " +
							"AND requestType = :requestType " +
							(phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
							(fromDateFill ? "AND :fromDate <= createdDate " : "") +
							(toDateFill ? "AND :toDate >= createdDate " : "");

		String selectJpql =
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND createdBy IN (:userIds) " +
							"AND requestType = :requestType " +
							(phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
							(fromDateFill ? "AND :fromDate <= createdDate " : "") +
							(toDateFill ? "AND :toDate >= createdDate " : "") +
						"ORDER BY createdDate DESC ";
		// @formatter:on
		try {
			TypedQuery<Long> countQuery =
							entityManager
											.createQuery(countJpql, Long.class)
											.setParameter("userIds", userIds)
											.setParameter("requestType", requestType)
											.setParameter("deleted", false);

			TypedQuery<CallResponse> selectQuery = entityManager
							.createQuery(selectJpql, clazz)
							.setParameter("deleted", false)
							.setParameter("requestType", requestType)
							.setParameter("userIds", userIds)
							.setFirstResult(toFilter.skip())
							.setMaxResults(toFilter.getPageSize());

			if (phoneNumberFill) {
				selectQuery.setParameter("phoneNumber", "%" + toFilter.getPhoneNumber() + "%");
				countQuery.setParameter("phoneNumber", "%" + toFilter.getPhoneNumber() + "%");
			}
			if (fromDateFill) {
				selectQuery.setParameter("fromDate", toFilter.getFromDate());
				countQuery.setParameter("fromDate", toFilter.getFromDate());
			}
			if (toDateFill) {
				selectQuery.setParameter("toDate", toFilter.getToDate());
				countQuery.setParameter("toDate", toFilter.getToDate());
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
