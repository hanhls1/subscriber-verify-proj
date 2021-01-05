package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestStatus;
import vn.metech.dto.MtIdFilter;
import vn.metech.dto.request.MtIdRequest;
import vn.metech.entity.IdRequest;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IIdRequestRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class IdRequestRepositoryImpl
				extends RepositoryImpl<IdRequest> implements IIdRequestRepository {

	public IdRequestRepositoryImpl(EntityManager entityManager) {
		super(entityManager, IdRequest.class);
	}

	@Override
	public IdRequest findDuplicateIdRequestIncludeResponseBy(MtIdRequest mtAdRequest, Date checkAfter) {
		try {
			String jpql = "select irq from " + clazz.getName() + " irq left join fetch irq.idResponse " +
							" where irq.phoneNumber = :phoneNumber and irq.createdDate > :checkAfter " +
							" and irq.duplicate = :duplicate and irq.idNumber = :idNumber ";
			return entityManager.createQuery(jpql, clazz)
							.setParameter("phoneNumber", mtAdRequest.getPhoneNumber())
							.setParameter("idNumber", mtAdRequest.getIdNumber())
							.setParameter("checkAfter", checkAfter).setParameter("duplicate", false)
							.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<IdRequest> getRequestsIncludeResponseBy(
					int limit,
					Date fetchAfter,
					int fetchTimesLimit,
					boolean dupExclude,
					RequestStatus... requestStatus) {
		// @formatter:off
		String jpql = "SELECT ireq " +
									"FROM " + clazz.getName() + " ireq " +
										"LEFT JOIN FETCH ireq.idResponse ires " +
									"WHERE ireq.deleted = :deleted " +
										"AND ires.deleted = :deleted " +
										(dupExclude ? "AND ireq.duplicate = :duplicate " : "") +
										"AND (ireq.lastFetch IS NULL OR ireq.lastFetch < :lastFetch) " +
                    "AND ireq.fetchTimes <= :fetchTimes " +
										"AND ireq.requestStatus IN (:requestStatus)";
		// @formatter:on
		try {
			TypedQuery<IdRequest> query = entityManager
							.createQuery(jpql, clazz)
							.setParameter("deleted", false)
							.setParameter("lastFetch", fetchAfter)
							.setParameter("fetchTimes", fetchTimesLimit)
							.setParameter(
											"requestStatus",
											Arrays.asList(requestStatus)
							);
			if (dupExclude) {
				query.setParameter("duplicate", false);
			}

			return query.setMaxResults(limit)
							.getResultList();
		} catch (Exception e) {

			return Collections.emptyList();
		}
	}

	@Override
	public PagedResult<IdRequest> getRequestsIncludeResponseBy(MtIdFilter idFilter, List<String> userIds) {
		boolean phoneFill = !StringUtils.isEmpty(idFilter.getPhoneNumber());
		boolean fromDateFill = idFilter.getFromDate() != null;
		boolean toDateFill = idFilter.getToDate() != null;
		// @formatter:off
		String countJpql =
										"SELECT COUNT(ireq.id) " +
										"FROM " + clazz.getName() + " ireq " +
										"WHERE ireq.deleted = :deleted " +
											"AND ireq.createdBy IN (:userIds) " +
											(phoneFill ? "AND ireq.phoneNumber LIKE :phoneNumber " : " ") +
											(fromDateFill ? "AND ireq.createdDate >= :fromDate " : " ") +
											(toDateFill ? "AND ireq.createdDate <= :toDate " : " ");

		String selectJpql =
										"SELECT ireq " +
										"FROM " + clazz.getName() + " ireq " +
											"LEFT JOIN FETCH ireq.idResponse ires " +
										"WHERE ireq.deleted = :deleted " +
											"AND ires.deleted = :deleted " +
											"AND ireq.createdBy IN (:userIds) " +
											(phoneFill ? "AND ireq.phoneNumber LIKE :phoneNumber " : " ") +
											(fromDateFill ? "AND ireq.createdDate >= :fromDate " : " ") +
											(toDateFill ? "AND ireq.createdDate <= :toDate " : " ") +
										"ORDER BY ireq.createdDate DESC ";
		// @formatter:on
		try {
			TypedQuery<Long> countQuery = entityManager
							.createQuery(countJpql, Long.class)
							.setParameter("deleted", false)
							.setParameter("userIds", userIds);

			TypedQuery<IdRequest> selectQuery = entityManager
							.createQuery(selectJpql, clazz)
							.setParameter("deleted", false)
							.setParameter("userIds", userIds);

			if (phoneFill) {
				countQuery.setParameter("phoneNumber", "%" + idFilter.getPhoneNumber() + "%");
				selectQuery.setParameter("phoneNumber", "%" + idFilter.getPhoneNumber() + "%");
			}
			if (fromDateFill) {
				countQuery.setParameter("fromDate", idFilter.getFromDate());
				selectQuery.setParameter("fromDate", idFilter.getFromDate());
			}
			if (toDateFill) {
				countQuery.setParameter("toDate", idFilter.getToDate());
				selectQuery.setParameter("toDate", idFilter.getToDate());
			}

			return new PagedResult<>(
							countQuery.getSingleResult().intValue(),
							selectQuery.setFirstResult(idFilter.skip())
											.setMaxResults(idFilter.take()).getResultList()
			);
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}

	@Override
	public PagedResult<IdRequest> getRequestsBy(MtIdFilter idFilter, List<String> userIds) {
		boolean phoneFill = !StringUtils.isEmpty(idFilter.getPhoneNumber());
		boolean fromDateFill = idFilter.getFromDate() != null;
		boolean toDateFill = idFilter.getToDate() != null;
		// @formatter:off
		String countJpql =
										"SELECT COUNT(ireq.id) " +
										"FROM " + clazz.getName() + " ireq " +
										"WHERE ireq.deleted = :deleted " +
											"AND ireq.createdBy IN (:userIds) " +
											(phoneFill ? "AND ireq.phoneNumber LIKE :phoneNumber " : " ") +
											(fromDateFill ? "AND ireq.createdDate >= :fromDate " : " ") +
											(toDateFill ? "AND ireq.createdDate <= :toDate " : " ");

		String selectJpql =
										"SELECT ireq " +
										"FROM " + clazz.getName() + " ireq " +
										"WHERE ireq.deleted = :deleted " +
											"AND ireq.createdBy IN (:userIds) " +
											(phoneFill ? "AND ireq.phoneNumber LIKE :phoneNumber " : " ") +
											(fromDateFill ? "AND ireq.createdDate >= :fromDate " : " ") +
											(toDateFill ? "AND ireq.createdDate <= :toDate " : " ") +
										"ORDER BY ireq.createdDate DESC ";
		// @formatter:on
		try {
			TypedQuery<Long> countQuery = entityManager
							.createQuery(countJpql, Long.class)
							.setParameter("deleted", false)
							.setParameter("userIds", userIds);

			TypedQuery<IdRequest> selectQuery = entityManager
							.createQuery(selectJpql, clazz)
							.setParameter("deleted", false)
							.setParameter("userIds", userIds);

			if (phoneFill) {
				countQuery.setParameter("phoneNumber", "%" + idFilter.getPhoneNumber() + "%");
				selectQuery.setParameter("phoneNumber", "%" + idFilter.getPhoneNumber() + "%");
			}
			if (fromDateFill) {
				countQuery.setParameter("fromDate", idFilter.getFromDate());
				selectQuery.setParameter("fromDate", idFilter.getFromDate());
			}
			if (toDateFill) {
				countQuery.setParameter("toDate", idFilter.getToDate());
				selectQuery.setParameter("toDate", idFilter.getToDate());
			}

			return new PagedResult<>(
							countQuery.getSingleResult().intValue(),
							selectQuery.setFirstResult(idFilter.skip())
											.setMaxResults(idFilter.take()).getResultList()
			);
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}
}
