package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestStatus;
import vn.metech.dto.MtAdFilter;
import vn.metech.dto.request.MtAdRequest;
import vn.metech.entity.AdRequest;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IAdRequestRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class AdRequestRepositoryImpl
        extends RepositoryImpl<AdRequest> implements IAdRequestRepository {

    public AdRequestRepositoryImpl(EntityManager entityManager) {
        super(entityManager, AdRequest.class);
    }

    @Override
    public long countRequestsBy(String customerRequestAd) {
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
                    .setParameter("customerRequestId", customerRequestAd)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public AdRequest findDuplicateAdRequestIncludeResponseBy(MtAdRequest mtAdRequest, Date checkAfter) {
        try {
            String jpql = "select arq from " + clazz.getName() + " arq left join fetch arq.adResponse " +
                    " where arq.phoneNumber = :phoneNumber and arq.createdDate > :checkAfter " +
                    " order by arq.createdDate desc ";
            return entityManager.createQuery(jpql, clazz)
                    .setParameter("phoneNumber", mtAdRequest.getPhoneNumber())
                    .setParameter("checkAfter", checkAfter)
                    .setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AdRequest> getRequestsIncludeResponseBy(
            int limit,
            Date fetchAfter,
            int fetchLimit,
            RequestStatus... requestStatus) {
        // @formatter:off
        String jpql = "SELECT areq " +
                "FROM " + clazz.getName() + " areq " +
                "LEFT JOIN FETCH areq.adResponse ares " +
                "WHERE areq.deleted = :deleted " +
                "AND ares.deleted = :deleted " +
                "AND (areq.lastFetch IS NULL OR areq.lastFetch < :lastFetch) " +
                "AND areq.fetchTimes <= :fetchTimes " +
                "AND areq.requestStatus IN (:requestStatus)";
        // @formatter:on
        try {
            return entityManager
                    .createQuery(jpql, clazz)
                    .setParameter("deleted", false)
                    .setParameter("lastFetch", fetchAfter)
                    .setParameter("fetchTimes", fetchLimit)
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
    public AdRequest getRequestBy(String requestAd, RequestStatus... statuses) {
        boolean requestFill = statuses != null && statuses.length > 0;
        // @formatter:off
        String jpql =
                "FROM " + clazz.getName() + " areq " +
                        "LEFT JOIN FETCH areq.adResponse ares " +
                        "WHERE areq.deleted = :deleted " +
                        "AND ares.deleted = :deleted " +
                        "AND areq.id = :requestId " +
                        (requestFill ? "AND areq.requestStatus IN (:requestStatuses)" : "");
        // @formatter:on
        try {
            TypedQuery<AdRequest> adQuery = entityManager
                    .createQuery(jpql, clazz)
                    .setParameter("deleted", false)
                    .setParameter("requestId", requestAd);
            if (requestFill) {
                adQuery.setParameter("requestStatuses", Arrays.asList(statuses));
            }

            return adQuery.setMaxResults(1).getSingleResult();
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    public PagedResult<AdRequest> getRequestsIncludeResponseBy(MtAdFilter adFilter, List<String> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new PagedResult<>();
        }
        boolean phoneFill = !StringUtils.isEmpty(adFilter.getPhoneNumber());
        boolean fromDateFill = adFilter.getFromDate() != null;
        boolean toDateFill = adFilter.getToDate() != null;
        // @formatter:off
        String countJpql = "SELECT COUNT(id) " +
                "FROM " + clazz.getName() + " " +
                "WHERE deleted = :deleted " +
                "AND adResponse.deleted = :deleted " +
                "AND createdBy IN (:createdBy) " +
                (phoneFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                (fromDateFill ? "AND createdDate >= :fromDate " : "") +
                (toDateFill ? "AND createdDate <= :toDate " : "");

        String selectJpql = "SELECT areq " +
                "FROM " + clazz.getName() + " areq " +
                "LEFT JOIN FETCH areq.adResponse ares " +
                "WHERE areq.deleted = :deleted " +
                "AND ares.deleted = :deleted " +
                "AND areq.createdBy IN (:createdBy) " +
                (phoneFill ? "AND areq.phoneNumber LIKE :phoneNumber " : "") +
                (fromDateFill ? "AND areq.createdDate >= :fromDate " : "") +
                (toDateFill ? "AND areq.createdDate <= :toDate " : "") +
                "ORDER BY areq.createdDate DESC ";
        // @formatter:on
        try {
            TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class)
                    .setParameter("deleted", false)
                    .setParameter("createdBy", userIds);

            TypedQuery<AdRequest> selectQuery = entityManager.createQuery(selectJpql, clazz)
                    .setParameter("deleted", false)
                    .setParameter("createdBy", userIds);

            if (phoneFill) {
                countQuery.setParameter("phoneNumber", "%" + adFilter.getPhoneNumber() + "%");
                selectQuery.setParameter("phoneNumber", "%" + adFilter.getPhoneNumber() + "%");
            }
            if (fromDateFill) {
                countQuery.setParameter("fromDate", adFilter.getFromDate());
                selectQuery.setParameter("fromDate", adFilter.getFromDate());
            }
            if (toDateFill) {
                countQuery.setParameter("toDate", adFilter.getToDate());
                selectQuery.setParameter("toDate", adFilter.getToDate());
            }

            return new PagedResult<>(
                    countQuery.getSingleResult().intValue(),
                    selectQuery.setFirstResult(adFilter.skip())
                            .setMaxResults(adFilter.take()).getResultList()
            );
        } catch (Exception e) {
            return new PagedResult<>();
        }
    }

    @Override
    public PagedResult<AdRequest> getRequestsBy(MtAdFilter mtAdFilter, List<String> userIds) {
        boolean phoneFill = !StringUtils.isEmpty(mtAdFilter.getPhoneNumber());
        boolean statusFill = mtAdFilter.getRequestStatus() != null;
        boolean fromDateFill = mtAdFilter.getFromDate() != null;
        boolean toDateFill = mtAdFilter.getToDate() != null;
        boolean typeFill = mtAdFilter.getServiceType() != null;
        // @formatter:off
        String countJpql =
                "SELECT COUNT(ireq.id) " +
                        "FROM " + clazz.getName() + " ireq " +
                        "WHERE ireq.deleted = :deleted " +
                        "AND ireq.createdBy IN (:userIds) " +
                        (phoneFill ? "AND ireq.phoneNumber LIKE :phoneNumber " : " ") +
                        (statusFill ? "AND ireq.requestStatus = :requestStatus " : " ") +
                        (fromDateFill ? "AND ireq.createdDate >= :fromDate " : " ") +
                        (toDateFill ? "AND ireq.createdDate <= :toDate " : " ") +
                        (typeFill ? "AND ireq.verifyService = :serviceType " : " ");

        String selectJpql =
                "SELECT ireq " +
                        "FROM " + clazz.getName() + " ireq " +
                        "WHERE ireq.deleted = :deleted " +
                        "AND ireq.createdBy IN (:userIds) " +
                        (phoneFill ? "AND ireq.phoneNumber LIKE :phoneNumber " : " ") +
                        (statusFill ? "AND ireq.requestStatus = :requestStatus " : " ") +
                        (fromDateFill ? "AND ireq.createdDate >= :fromDate " : " ") +
                        (toDateFill ? "AND ireq.createdDate <= :toDate " : " ") +
                        (typeFill ? "AND ireq.verifyService = :serviceType " : " ") +
                        "ORDER BY ireq.createdDate DESC ";

        // @formatter:on
        try {
            TypedQuery<Long> countQuery = entityManager
                    .createQuery(countJpql, Long.class)
                    .setParameter("deleted", false)
                    .setParameter("userIds", userIds);

            TypedQuery<AdRequest> selectQuery = entityManager
                    .createQuery(selectJpql, clazz)
                    .setParameter("deleted", false)
                    .setParameter("userIds", userIds);

            if (phoneFill) {
                countQuery.setParameter("phoneNumber", "%" + mtAdFilter.getPhoneNumber() + "%");
                selectQuery.setParameter("phoneNumber", "%" + mtAdFilter.getPhoneNumber() + "%");
            }
            if (statusFill) {
                countQuery.setParameter("requestStatus", mtAdFilter.getRequestStatus());
                selectQuery.setParameter("requestStatus", mtAdFilter.getRequestStatus());
            }
            if (fromDateFill) {
                countQuery.setParameter("fromDate", mtAdFilter.getFromDate());
                selectQuery.setParameter("fromDate", mtAdFilter.getFromDate());
            }
            if (toDateFill) {
                countQuery.setParameter("toDate", mtAdFilter.getToDate());
                selectQuery.setParameter("toDate", mtAdFilter.getToDate());
            }
            if (typeFill) {
                countQuery.setParameter("serviceType", mtAdFilter.getServiceType());
                selectQuery.setParameter("serviceType", mtAdFilter.getServiceType());
            }

            return new PagedResult<>(
                    countQuery.getSingleResult().intValue(),
                    selectQuery.setFirstResult(mtAdFilter.skip())
                            .setMaxResults(mtAdFilter.take()).getResultList()
            );
        } catch (Exception e) {
            return new PagedResult<>();
        }
    }

    @Override
    public List<AdRequest> getGroupSyncibleRequestsIncludeResponseBy(int numberOfRecords, List<String> userIds) {
//		Date beginDay = DateUtils.beginDay(null);
//		Date endDay = DateUtils.endDay(null);
        // @formatter:off
        String jpql = "SELECT areq " +
                "FROM " + clazz.getName() + " areq " +
                "LEFT JOIN FETCH areq.adResponse ares " +
                "WHERE areq.deleted = :deleted " +
                "AND ares.deleted = :deleted " +
                "AND areq.duplicate = :duplicate " +
                "AND areq.createdBy IN (:userIds) " +
                "AND areq.groupSync = :groupSync " +
//										"AND areq.createdDate BETWEEN :from AND :to " +
                "ORDER BY areq.updatedDate DESC ";
        // @formatter:on
        try {
            return entityManager.createQuery(jpql, clazz).setParameter("deleted", false)
                    .setParameter("userIds", userIds).setParameter("duplicate", false)
                    .setParameter("groupSync", false)
//                    .setParameter("from", beginDay).setParameter("to", endDay)
                    .setMaxResults(numberOfRecords).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
