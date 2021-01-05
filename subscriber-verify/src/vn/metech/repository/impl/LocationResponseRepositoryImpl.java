package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestType;
import vn.metech.dto.MtLocationFilter;
import vn.metech.entity.LocationResponse;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ILocationResponseRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class LocationResponseRepositoryImpl
				extends RepositoryImpl<LocationResponse> implements ILocationResponseRepository {

	public LocationResponseRepositoryImpl(EntityManager entityManager) {
		super(entityManager, LocationResponse.class);
	}


	@Override
	public PagedResult<LocationResponse> getResponsesBy(
					MtLocationFilter filter, RequestType requestType, List<String> userIds) {
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
						"ORDER BY createdDate DESC ";
		// @formatter:on
		try {
			TypedQuery<Long> countQuery =
							entityManager
											.createQuery(countJpql, Long.class)
											.setParameter("deleted", false)
											.setParameter("userIds", userIds);

			TypedQuery<LocationResponse> selectQuery = entityManager
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

			return new PagedResult<>(countQuery.getSingleResult().intValue(), selectQuery.getResultList());
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}
}
