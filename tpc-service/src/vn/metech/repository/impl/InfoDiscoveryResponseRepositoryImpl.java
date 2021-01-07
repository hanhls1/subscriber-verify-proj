package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestType;
import vn.metech.constant.ResponseStatus;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IInfoDiscoveryResponseRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

@Repository
public class InfoDiscoveryResponseRepositoryImpl
				extends RepositoryImpl<InfoDiscoveryResponse> implements IInfoDiscoveryResponseRepository {

	public InfoDiscoveryResponseRepositoryImpl(EntityManager entityManager) {
		super(entityManager, InfoDiscoveryResponse.class);
	}

	@Override
	public InfoDiscoveryResponse getResponseIncludeRequestsBy(String responseId, ResponseStatus... statuses) {
		// @formatter:off
		String jpql =
				"SELECT ires " +
				"FROM " + clazz.getName() + " ires " +
					"LEFT JOIN FETCH ires.infoDiscoveryRequests ireq " +
				"WHERE ires.deleted = :deleted " +
          "AND ireq.deleted = :deleted " +
					"AND ires.id = :responseId " +
					"AND ires.responseStatus IN (:statuses)";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("responseId", responseId)
							.setParameter("statuses", Arrays.asList(statuses))
							.setMaxResults(1)
							.getSingleResult();
		} catch (Exception ignored) {
			return null;
		}
	}

	@Override
	public PagedResult<InfoDiscoveryResponse> getResponsesBy(
					MtInfoDiscoveryFilter filter, RequestType requestType, List<String> userIds) {
		if (userIds == null || userIds.isEmpty()) {
			return new PagedResult<>();
		}
		boolean idNumberFill = !StringUtils.isEmpty(filter.getIdNumber());
		boolean fromDateFill = filter.getFromDate() != null;
		boolean toDateFill = filter.getToDate() != null;
		// @formatter:off
		String countJpql =
						"SELECT COUNT(id) " +
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND requestType = :requestType " +
							"AND createdBy IN (:userIds) " +
							(idNumberFill ? "AND idNumber LIKE :idNumber " : "") +
							(fromDateFill ? "AND :fromDate <= createdDate " : "") +
							(toDateFill ? "AND :toDate >= createdDate " : "");

		String selectJpql =
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND requestType = :requestType " +
							"AND createdBy IN (:userIds) " +
							(idNumberFill ? "AND idNumber LIKE :idNumber " : "") +
							(fromDateFill ? "AND :fromDate <= createdDate " : "") +
							(toDateFill ? "AND :toDate >= createdDate " : "") +
						"ORDER BY createdDate DESC ";
		// @formatter:on
		try {
			TypedQuery<Long> countQuery =
							entityManager
											.createQuery(countJpql, Long.class)
											.setParameter("deleted", false)
											.setParameter("requestType", requestType)
											.setParameter("userIds", userIds);

			TypedQuery<InfoDiscoveryResponse> selectQuery = entityManager
							.createQuery(selectJpql, clazz)
							.setParameter("deleted", false)
							.setParameter("requestType", requestType)
							.setParameter("userIds", userIds)
							.setFirstResult(filter.skip())
							.setMaxResults(filter.getPageSize());

			if (idNumberFill) {
				selectQuery.setParameter("idNumber", "%" + filter.getIdNumber() + "%");
				countQuery.setParameter("idNumber", "%" + filter.getIdNumber() + "%");
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
