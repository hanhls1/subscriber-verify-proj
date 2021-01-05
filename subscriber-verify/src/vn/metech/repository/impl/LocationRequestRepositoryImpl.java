package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestType;
import vn.metech.dto.request.MtLocationFilterRequest;
import vn.metech.dto.request.MtLocationRequest;
import vn.metech.entity.LocationRequest;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ILocationRequestRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class LocationRequestRepositoryImpl
				extends RepositoryImpl<LocationRequest> implements ILocationRequestRepository {

	public LocationRequestRepositoryImpl(EntityManager entityManager) {
		super(entityManager, LocationRequest.class);
	}

//	@Override
//	public long countRequestsBy(String customerRequestId) {
//		// @formatter:off
//		String jpql = "SELECT COUNT(customerRequestId) " +
//									"FROM " + clazz.getName() + " " +
//									"WHERE deleted = :deleted AND customerRequestId = :customerRequestId";
//		// @formatter:on
//		try {
//			return entityManager
//							.createQuery(jpql, Long.class)
//							.setParameter("deleted", false)
//							.setParameter("customerRequestId", customerRequestId)
//							.getSingleResult();
//		} catch (Exception e) {
//
//			return 0;
//		}
//	}


	@Override
	public PagedResult<LocationRequest> getRequestsBy(
					MtLocationFilterRequest locationFilter, RequestType requestType, List<String> userIds) {
		boolean phoneNumberFill = !StringUtils.isEmpty(locationFilter.getPhoneNumber());
		boolean fromDateFill = locationFilter.getFromDate() != null;
		boolean toDateFill = locationFilter.getToDate() != null;

		// @formatter:off
		String countJpql =
						"SELECT COUNT(id) " +
						"FROM " + clazz.getName() + " " +
						"WHERE deleted = :deleted " +
							"AND createdBy IN (:userIds) " +
							"AND requestType = :requestType " +
							"AND verifyService = :serviceType " +
							(phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
							(fromDateFill ? "AND :fromDate <= createdDate " : "") +
							(toDateFill ? "AND :toDate >= createdDate " : "");

		String selectJpql =
						"SELECT lreq " +
						"FROM " + clazz.getName() + " lreq " +
						"WHERE lreq.deleted = :deleted " +
							"AND lreq.createdBy IN (:userIds) " +
							"AND lreq.requestType = :requestType " +
							"AND lreq.verifyService = :serviceType " +
							(phoneNumberFill ? "AND lreq.phoneNumber LIKE :phoneNumber " : "") +
							(fromDateFill ? "AND :fromDate <= lreq.createdDate " : "") +
							(toDateFill ? "AND :toDate >= lreq.createdDate " : "") +
						"ORDER BY lreq.createdDate DESC ";
		// @formatter:on
		try {

			TypedQuery<Long> countQuery =
							entityManager
											.createQuery(countJpql, Long.class)
											.setParameter("userIds", userIds)
											.setParameter("requestType", requestType)
											.setParameter("serviceType", locationFilter.getServiceType())
											.setParameter("deleted", false);

			TypedQuery<LocationRequest> selectQuery = entityManager
							.createQuery(selectJpql, clazz)
							.setParameter("deleted", false)
							.setParameter("requestType", requestType)
							.setParameter("serviceType", locationFilter.getServiceType())
							.setParameter("userIds", userIds);

			if (phoneNumberFill) {
				selectQuery.setParameter("phoneNumber", "%" + locationFilter.getPhoneNumber() + "%");
				countQuery.setParameter("phoneNumber", "%" + locationFilter.getPhoneNumber() + "%");
			}
			if (fromDateFill) {
				selectQuery.setParameter("fromDate", locationFilter.getFromDate());
				countQuery.setParameter("fromDate", locationFilter.getFromDate());
			}
			if (toDateFill) {
				selectQuery.setParameter("toDate", locationFilter.getToDate());
				countQuery.setParameter("toDate", locationFilter.getToDate());
			}

			return new PagedResult<>(
							countQuery.getSingleResult().intValue(),
							selectQuery
											.setFirstResult(locationFilter.skip())
											.setMaxResults(locationFilter.getPageSize()).getResultList()
			);
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}

	@Override
	public LocationRequest findDuplicateLocationRequestIncludeResponseBy(
					MtLocationRequest mtRequest, Date checkDuplicateAfter) {
		try {
			String jpql = "select lrq from " + clazz.getName() + " lrq left join fetch lrq.locationResponse " +
							"where lrq.phoneNumber = :phoneNumber and lrq.createdDate > :checkAfter and lrq.duplicate = :duplicate " +
							"and lrq.home = :home and lrq.work = :work and lrq.ref = :ref ";
			return entityManager.createQuery(jpql, clazz)
							.setParameter("phoneNumber", mtRequest.getPhoneNumber())
							.setParameter("home", mtRequest.getHomeAddress().getAddress())
							.setParameter("work", mtRequest.getWorkAddress().getAddress())
							.setParameter("ref", mtRequest.getReferAddress().getAddress())
							.setParameter("checkAfter", checkDuplicateAfter).setParameter("duplicate", false)
							.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
