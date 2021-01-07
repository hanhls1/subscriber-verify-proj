package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.RequestType;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.dto.request.MtInfoDiscoveryRequest;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IInfoDiscoveryRequestRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class InfoDiscoveryRequestRepositoryImpl
				extends RepositoryImpl<InfoDiscoveryRequest> implements IInfoDiscoveryRequestRepository {

	public InfoDiscoveryRequestRepositoryImpl(EntityManager entityManager) {
		super(entityManager, InfoDiscoveryRequest.class);
	}

	@Override
	public long countRequestsBy(String customerRequestId) {
		// @formatter:off
		String jpql = "SELECT COUNT(customerRequestId) " +
									"FROM " + clazz.getName() + " " +
									"WHERE deleted = :deleted " +
										"AND customerRequestId = :customerRequestId";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, Long.class)
							.setParameter("deleted", false)
							.setParameter("customerRequestId", customerRequestId)
							.getSingleResult();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public InfoDiscoveryRequest getRequestBy(MtInfoDiscoveryRequest mtInfoDiscoveryRequest, RequestStatus... statuses) {
		// @formatter:off
		String jpql = "FROM " + clazz.getName() + " " +
									"WHERE deleted = :deleted " +
										"AND idNumber = :idNumber " +
										"AND requestStatus IN (:requestStatus) " +
									"ORDER BY createdDate DESC";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("idNumber", mtInfoDiscoveryRequest.getIdNumber())
							.setParameter("requestStatus", Arrays.asList(statuses))
							.setMaxResults(1)
							.getSingleResult();
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public InfoDiscoveryRequest getRequestIncludeResponseBy(
					MtInfoDiscoveryRequest mtInfoDiscoveryRequest, RequestStatus... statuses) {
		boolean statusFill = statuses != null && statuses.length > 0;
		// @formatter:off
		String jpql =
									"SELECT ireq " +
									"FROM " + clazz.getName() + " ireq " +
										"LEFT JOIN FETCH ireq.infoDiscoveryResponse ires " +
									"WHERE ireq.deleted = :deleted " +
										"AND ires.deleted = :deleted " +
										"AND ireq.idNumber = :idNumber " +
										(statusFill ? "AND ireq.requestStatus IN (:requestStatus) " : " ") +
									"ORDER BY ireq.createdDate DESC";
		// @formatter:on
		try {
			TypedQuery<InfoDiscoveryRequest> idRequestQuery = entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("idNumber", mtInfoDiscoveryRequest.getIdNumber());

			if (statusFill) {
				idRequestQuery
								.setParameter("requestStatus", Arrays.asList(statuses));
			}

			return idRequestQuery.setMaxResults(1)
							.getSingleResult();
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public List<InfoDiscoveryRequest> getRequestsBy(int limit, RequestStatus... requestStatus) {
		// @formatter:off
		String jpql = "FROM " + clazz.getName() + " " +
									"WHERE deleted = :deleted " +
										"AND requestStatus IN (:requestStatus) ";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter(
											"requestStatus",
											Arrays.asList(requestStatus)
							)
							.setMaxResults(limit)
							.getResultList();
		} catch (Exception e) {

			return Collections.emptyList();
		}
	}

	@Override
	public List<InfoDiscoveryRequest> getRequestsIncludeResponseBy(
					int limit, Date fetchAfter,
					int fetchTimesLimit, boolean dupExclude, RequestStatus... requestStatus) {
		// @formatter:off
		String jpql = "SELECT ireq " +
									"FROM " + clazz.getName() + " ireq " +
										"LEFT JOIN FETCH ireq.infoDiscoveryResponse ires " +
									"WHERE ireq.deleted = :deleted " +
										"AND ires.deleted = :deleted " +
										(dupExclude ? "AND ireq.duplicate = :duplicate " : "") +
										"AND (ireq.lastFetch IS NULL OR ireq.lastFetch < :lastFetch) " +
                    "AND ireq.fetchTimes <= :fetchTimes " +
										"AND ireq.requestStatus IN (:requestStatus)";
		// @formatter:on
		try {
			TypedQuery<InfoDiscoveryRequest> query = entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("lastFetch", fetchAfter)
							.setParameter("fetchTimes", fetchTimesLimit)
							.setParameter("requestStatus", Arrays.asList(requestStatus));
			if (dupExclude) {
				query.setParameter("duplicate", false);
			}

			return query.setMaxResults(limit).getResultList();
		} catch (Exception e) {

			return Collections.emptyList();
		}
	}

	@Override
	public InfoDiscoveryRequest getRequestIncludeResponseBy(String requestId, RequestStatus... statuses) {
		// @formatter:off
		String jpql =
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
						"AND id = :requestId " +
						"AND requestStatus IN (:requestStatuses) ";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("requestId", requestId)
							.setParameter("requestStatuses", Arrays.asList(statuses))
							.setMaxResults(1)
							.getSingleResult();
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public InfoDiscoveryRequest getRequestBy(String requestId, RequestStatus... statuses) {
		// @formatter:off
		String jpql =
						"FROM " + clazz.getName() + " ireq " +
							"LEFT JOIN FETCH ireq.infoDiscoveryResponse ires " +
						"WHERE ireq.deleted = :deleted " +
							"AND ires.deleted = :deleted " +
							"AND ireq.id = :requestId " +
							"AND ireq.requestStatus IN (:requestStatuses)";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("requestId", requestId)
							.setParameter("requestStatuses", Arrays.asList(statuses))
							.setMaxResults(1)
							.getSingleResult();
		} catch (Exception ignored) {
			return null;
		}
	}

	@Override
	public InfoDiscoveryRequest getCustomerRequestIncludeResponseBy(
					String customerRequestId, List<String> userIds, RequestStatus... statuses) {
		if (userIds == null || userIds.isEmpty()) {
			return null;
		}
		// @formatter:off
		String jpql =
						"FROM " + clazz.getName() + " ireq " +
							"LEFT JOIN FETCH ireq.infoDiscoveryResponse ires " +
						"WHERE ireq.deleted = :deleted " +
							"AND ires.deleted = :deleted " +
							"AND ireq.createdBy IN (:userIds) " +
							"AND ireq.customerRequestId = :customerRequestId " +
							"AND ireq.requestStatus IN (:requestStatuses)";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("userIds", userIds)
							.setParameter("customerRequestId", customerRequestId)
							.setParameter("requestStatuses", Arrays.asList(statuses))
							.setMaxResults(1)
							.getSingleResult();
		} catch (Exception ignored) {
			return null;
		}
	}

	@Override
	public List<InfoDiscoveryRequest> getRequestsIncludeResponseBy(
					int limit, Date createdBefore, RequestStatus... statuses) {
		// @formatter:off
		String jpql = "SELECT ireq " +
									"FROM " + clazz.getName() + " ireq " +
										"LEFT JOIN FETCH ireq.infoDiscoveryResponse ires " +
									"WHERE ireq.deleted = :deleted " +
										"AND ires.deleted = :deleted " +
										"AND ireq.createdDate < :createdBefore " +
										"AND ireq.requestStatus IN (:requestStatus) " +
									"ORDER BY ireq.createdDate";
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("createdBefore", createdBefore)
							.setParameter("requestStatus", Arrays.asList(statuses))
							.setMaxResults(limit)
							.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<InfoDiscoveryRequest> getRequestsIncludeResponseBy(
					int limit, Date fetchBefore, Boolean charged, RequestStatus... statuses) {
		// @formatter:off
		String jpql = "SELECT ireq " +
									"FROM " + clazz.getName() + " ireq " +
										"LEFT JOIN FETCH ireq.infoDiscoveryResponse ires " +
									"WHERE ireq.deleted = :deleted " +
										"AND ires.deleted = :deleted " +
										"AND (ireq.charged IS NULL OR ireq.charged = :charged) " +
										"AND (ireq.lastFetch IS NULL OR ireq.lastFetch < :fetchBefore) " +
										"AND ireq.requestStatus IN (:requestStatus) "
						;
		// @formatter:on
		try {
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("charged", false)
							.setParameter("fetchBefore", fetchBefore)
							.setParameter("requestStatus", Arrays.asList(statuses))
							.setMaxResults(limit)
							.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public PagedResult<InfoDiscoveryRequest> getRequestsBy(
					MtInfoDiscoveryFilter infoDiscoveryFilter, RequestType requestType, List<String> userIds) {
		boolean idFill = !StringUtils.isEmpty(infoDiscoveryFilter.getIdNumber());
		boolean fromDateFill = infoDiscoveryFilter.getFromDate() != null;
		boolean toDateFill = infoDiscoveryFilter.getToDate() != null;
		try {
			// @formatter:off
			String countJpql = "SELECT COUNT(id) " +
													"FROM " + clazz.getName() + " " +
													"WHERE deleted = :deleted " +
														"AND requestType = :requestType " +
														"AND createdBy IN (:userIds) " +
														(idFill ? "AND idNumber LIKE :idNumber " : "")+
														(fromDateFill ? "AND createdDate >= :fromDate " : " ") +
														(toDateFill ? "AND createdDate <= :toDate " : " ");
			String selectJpql = "FROM " + clazz.getName() + " " +
													"WHERE deleted = :deleted " +
														"AND requestType = :requestType " +
														"AND createdBy IN (:userIds) " +
														(idFill ? "AND idNumber LIKE :idNumber " : "")+
														(fromDateFill ? "AND createdDate >= :fromDate " : " ") +
														(toDateFill ? "AND createdDate <= :toDate " : " ") +
													"ORDER BY createdDate DESC ";
			// @formatter:on
			TypedQuery<Long> countQuery = entityManager
							.createQuery(countJpql, Long.class)
							.setParameter("deleted", false)
							.setParameter("requestType", requestType)
							.setParameter("userIds", userIds);

			TypedQuery<InfoDiscoveryRequest> selectQuery = entityManager
							.createQuery(selectJpql, clazz)
							.setParameter("deleted", false)
							.setParameter("requestType", requestType)
							.setParameter("userIds", userIds);

			if (idFill) {
				countQuery.setParameter("idNumber", "%" + infoDiscoveryFilter.getIdNumber() + "%");
				selectQuery.setParameter("idNumber", "%" + infoDiscoveryFilter.getIdNumber() + "%");
			}
			if (fromDateFill) {
				countQuery.setParameter("fromDate", infoDiscoveryFilter.getFromDate());
				selectQuery.setParameter("fromDate", infoDiscoveryFilter.getFromDate());
			}
			if (toDateFill) {
				countQuery.setParameter("toDate", infoDiscoveryFilter.getToDate());
				selectQuery.setParameter("toDate", infoDiscoveryFilter.getToDate());
			}

			return new PagedResult<>(
							countQuery.getSingleResult().intValue(),
							selectQuery.setFirstResult(infoDiscoveryFilter.skip())
											.setMaxResults(infoDiscoveryFilter.take()).getResultList()
			);
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}
}
