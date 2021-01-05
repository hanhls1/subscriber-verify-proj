package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.constant.RequestType;
import vn.metech.dto.MtCallFilter;
import vn.metech.dto.request.MtCallRequest;
import vn.metech.entity.CallRequest;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.ICallRequestRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class CallRequestRepositoryImpl extends RepositoryImpl<CallRequest> implements ICallRequestRepository {

    public CallRequestRepositoryImpl(EntityManager entityManager) {
        super(entityManager, CallRequest.class);
    }


    @Override
    public CallRequest findDuplicateCallRequestIncludeResponseBy(MtCallRequest mtCallRequest, Date checkAfter) {
        try {
            String jpql = "select crq from " + clazz.getName() + " crq left join fetch crq.callResponse " +
                    " where crq.phoneNumber = :phoneNumber and crq.refPhone1 in (:refPhones) " +
                    " and crq.refPhone2 in (:refPhones) and crq.createdDate > :checkAfter " +
                    " order by crq.createdDate desc ";
            return entityManager.createQuery(jpql, clazz).setParameter("phoneNumber", mtCallRequest.getPhoneNumber())
                    .setParameter("refPhones", Arrays.asList(mtCallRequest.getRefPhone1(), mtCallRequest.getRefPhone2()))
                    .setParameter("checkAfter", checkAfter)
                    .setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public PagedResult<CallRequest> getRequestsBy(MtCallFilter filter, List<String> userIds) {
        boolean phoneNumberFill = !StringUtils.isEmpty(filter.getPhoneNumber());
        boolean fromDateFill = filter.getFromDate() != null;
        boolean toDateFill = filter.getToDate() != null;
        boolean typeFill = filter.getServiceType() != null;

        // @formatter:off
        String countJpql =
                "SELECT COUNT(id) " +
                        "FROM " + clazz.getName() + " " +
                        "WHERE deleted = :deleted " +
                        "AND createdBy IN (:userIds) " +
                        (phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                        (fromDateFill ? "AND :fromDate <= createdDate " : "") +
                        (toDateFill ? "AND :toDate >= createdDate " : "") +
                        (typeFill ? "AND verifyService = :serviceType " : "");

        String selectJpql =
                "FROM " + clazz.getName() + " " +
                        "WHERE deleted = :deleted " +
                        "AND createdBy IN (:userIds) " +
                        (phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                        (fromDateFill ? "AND :fromDate <= createdDate " : "") +
                        (toDateFill ? "AND :toDate >= createdDate " : "") +
                        (typeFill ? "AND verifyService = :serviceType " : "") +
                        "ORDER BY createdDate DESC ";
        // @formatter:on
        try {

            TypedQuery<Long> countQuery =
                    entityManager
                            .createQuery(countJpql, Long.class)
                            .setParameter("userIds", userIds)
                            .setParameter("deleted", false);

            TypedQuery<CallRequest> selectQuery = entityManager
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
            if (typeFill) {
                selectQuery.setParameter("serviceType", filter.getServiceType());
                countQuery.setParameter("serviceType", filter.getServiceType());
            }

            return new PagedResult<>(
                    countQuery.getSingleResult().intValue(),
                    selectQuery.getResultList()
            );
        } catch (Exception e) {
            return new PagedResult<>();
        }
    }

    @Override
    public PagedResult<CallRequest> getRequestsIncludeResponseBy(
            MtCallFilter filter, RequestType requestType, List<String> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new PagedResult<>();
        }
        boolean phoneNumberFill = !StringUtils.isEmpty(filter.getPhoneNumber());
        boolean fromDateFill = filter.getFromDate() != null;
        boolean toDateFill = filter.getToDate() != null;
        boolean typeFill = filter.getServiceType() != null;

        // @formatter:off
        String countJpql =
                "SELECT COUNT(id) " +
                        "FROM " + clazz.getName() + " " +
                        "WHERE deleted = :deleted " +
                        "AND callResponse.deleted = :deleted " +
                        "AND requestType = :requestType " +
                        "AND createdBy IN (:userId) " +
                        (phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                        (fromDateFill ? "AND :fromDate <= createdDate " : "") +
                        (toDateFill ? "AND :toDate >= createdDate " : "") +
                        (typeFill ? "AND verifyService = :serviceType " : "");

                String selectJpql =
                "SELECT creq " +
                        "FROM " + clazz.getName() + " creq " +
                        "LEFT JOIN FETCH creq.callResponse cres " +
                        "WHERE cres.deleted = :deleted " +
                        "AND creq.deleted = :deleted " +
                        "AND creq.requestType = :requestType " +
                        "AND creq.createdBy IN (:userId) " +
                        (phoneNumberFill ? "AND creq.phoneNumber LIKE :phoneNumber " : "") +
                        (fromDateFill ? "AND :fromDate <= creq.createdDate " : "") +
                        (toDateFill ? "AND :toDate >= creq.createdDate " : "") +
                        (typeFill ? "AND creq.verifyService = :serviceType " : "") +
                        "ORDER BY creq.createdDate DESC ";
        // @formatter:on
        try {

            TypedQuery<Long> countQuery =
                    entityManager
                            .createQuery(countJpql, Long.class)
                            .setParameter("userId", userIds)
                            .setParameter("requestType", requestType)
                            .setParameter("deleted", false);

            TypedQuery<CallRequest> selectQuery = entityManager
                    .createQuery(selectJpql, clazz)
                    .setParameter("deleted", false)
                    .setParameter("userId", userIds)
                    .setParameter("requestType", requestType)
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
            if (typeFill) {
                selectQuery.setParameter("serviceType", filter.getServiceType());
                countQuery.setParameter("serviceType", filter.getServiceType());
            }

            return new PagedResult<>(
                    countQuery.getSingleResult().intValue(),
                    selectQuery.getResultList()
            );
        } catch (Exception e) {
            e.printStackTrace();

            return new PagedResult<>();
        }
    }

    @Override
    public List<CallRequest> getGroupSyncibleRequestsIncludeResponseBy(int numberOfRecords, List<String> userIds) {
        Date beginDay = DateUtils.beginDay(null);
        Date endDay = DateUtils.endDay(null);
        // @formatter:off
        String jpql = "SELECT creq " +
                "FROM " + clazz.getName() + " creq " +
                "LEFT JOIN FETCH creq.callResponse cres " +
                "WHERE creq.deleted = :deleted " +
                "AND cres.deleted = :deleted " +
                "AND creq.duplicate = :duplicate " +
                "AND creq.createdBy IN (:userIds) " +
                "AND creq.createdDate BETWEEN :from AND :to " +
                "ORDER BY creq.updatedDate DESC "
//										"AND creq.groupSync = :groupSync "
                ;
        // @formatter:on
        try {
            return entityManager.createQuery(jpql, clazz).setParameter("deleted", false)
                    .setParameter("userIds", userIds).setParameter("duplicate", false)
                    .setParameter("from", beginDay).setParameter("to", endDay)
                    .setMaxResults(numberOfRecords).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
