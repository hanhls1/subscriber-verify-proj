package vn.metech.repository.impl;


import org.springframework.stereotype.Repository;
import vn.metech.common.ServiceType;
import vn.metech.dto.ConfirmInfoFilterIdResponse;
import vn.metech.dto.request.PageRequest;
import vn.metech.dto.request.monitor.ConfirmRequest;
import vn.metech.dto.request.monitor.FilterRequest;
import vn.metech.dto.response.ConfirmInfoResponse;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.SubPartnerResponse;
import vn.metech.entity.ConfirmInfo;
import vn.metech.repository.ConfirmInfoRepository;
import vn.metech.repository.base.RepositoryImpl;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class ConfirmInfoRepositoryImpl extends RepositoryImpl<ConfirmInfo> implements ConfirmInfoRepository {


    public ConfirmInfoRepositoryImpl(EntityManager em) {
        super(em, ConfirmInfo.class);
    }

    @Override
    public long countConfirmInfoByRequest(String requestId) {
        try {
            return em.createQuery(
                    "select count(id) from " + clazz.getName() + " where requestId = :requestId  ",
                    Long.class).setParameter("requestId", requestId).getSingleResult();
        } catch (Exception ignored) {
            return 0L;
        }
    }

    @Override
    public ConfirmInfo getConfirmInfoByQRequest(String qRequestId) {
        try {
            return em.createQuery(
                    "from " + clazz.getName() + " where qRequestId = :qRequestId  ", clazz)
                    .setParameter("qRequestId", qRequestId).setMaxResults(1).getSingleResult();
        } catch (Exception ignored) {
            return null;
        }
    }



    @Override
    public PageResponse<String> getConfirmInfoIdsBy(String phoneNumber, ServiceType serviceType, Date from, Date to, List<String> userIds, PageRequest page) {
        boolean phoneFill = !StringUtils.isEmpty(phoneNumber);
        boolean fromFill = from != null;
        boolean toFill = to != null;
        PageResponse<String> res = new PageResponse<>();
        res.setCurrentPage(page.getCurrentPage());
        res.setPageSize(page.getPageSize());
        try {

            String countQL = "select count(ci.id) from " + clazz.getName() +
                    " ci where ci.serviceType = :serviceType and ci.createdBy in (:userIds) " +
                    (phoneFill ? " and ci.phoneNumber like :phoneNumber " : "") + //
                    (fromFill ? " and ci.createdDate >= :from " : "") + //
                    (toFill ? " and ci.createdDate <= :to " : "") //
                    ;
            String selectQl = "select ci.id from " + clazz.getName() +
                    " ci where ci.serviceType = :serviceType and ci.createdBy in (:userIds) " +
                    (phoneFill ? " and ci.phoneNumber like :phoneNumber " : "") + //
                    (fromFill ? " and ci.createdDate >= :from " : "") + //
                    (toFill ? " and ci.createdDate <= :to " : "") //
                    ;
            TypedQuery<Long> countQuery = em.createQuery(countQL, Long.class)
                    .setParameter("serviceType", serviceType).setParameter("userIds", userIds);
            TypedQuery<String> selectQuery = em.createQuery(selectQl, String.class)
                    .setParameter("serviceType", serviceType).setParameter("userIds", userIds);

            if (phoneFill) {
                countQuery.setParameter("phoneNumber", "%" + phoneNumber + "%");
                selectQuery.setParameter("phoneNumber", "%" + phoneNumber + "%");
            }

            if (fromFill) {
                countQuery.setParameter("from", from);
                selectQuery.setParameter("from", from);
            }
            if (toFill) {
                countQuery.setParameter("to", to);
                selectQuery.setParameter("to", to);
            }

            res.setTotal(countQuery.getSingleResult());
            res.setData(selectQuery.setFirstResult(page.skip()).setMaxResults(page.getPageSize()).getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public ConfirmInfoFilterIdResponse fillConfirmInfoIds(
            String phoneNumber, ServiceType serviceType, Date from, Date to, List<String> userIds, PageRequest page) {
        boolean phoneNumberFill = !StringUtils.isEmpty(phoneNumber);
        boolean serviceTypeFill = serviceType != null;
        boolean fromFill = from != null;
        boolean toFill = to != null;

        ConfirmInfoFilterIdResponse response = new ConfirmInfoFilterIdResponse();
        if (userIds == null || userIds.isEmpty()) {
            return response;
        }
        try {
            String countQuery = "select count(ci.id) from ConfirmInfo ci where ci.createdBy in (:userIds) " +
                    (phoneNumberFill ? " and ci.phoneNumber = :phoneNumber " : "") +
                    (serviceTypeFill ? " and ci.serviceType = :serviceType " : "") +
                    (fromFill ? " and ci.createdDate >= :from " : "") +
                    (toFill ? " and ci.createdDate <= :to " : "");
            String selectQuery = "select ci.id from ConfirmInfo ci where ci.createdBy in (:userIds) " +
                    (phoneNumberFill ? " and ci.phoneNumber = :phoneNumber " : "") +
                    (serviceTypeFill ? " and ci.serviceType = :serviceType " : "") +
                    (fromFill ? " and ci.createdDate >= :from " : "") +
                    (toFill ? " and ci.createdDate <= :to " : "") +
                    "order by ci.createdDate  ";
            TypedQuery<Long> count = em.createQuery(countQuery, Long.class).setParameter("userIds", userIds);
            TypedQuery<String> select = em.createQuery(selectQuery, String.class)
                    .setParameter("userIds", userIds).setFirstResult(page.skip()).setMaxResults(page.getPageSize());
            if (phoneNumberFill) {
                count.setParameter("phoneNumber", phoneNumber);
                select.setParameter("phoneNumber", phoneNumber);
            }
            if (serviceTypeFill) {
                count.setParameter("serviceType", serviceType);
                select.setParameter("serviceType", serviceType);
            }
            if (fromFill) {
                count.setParameter("from", from);
                select.setParameter("from", from);
            }
            if (toFill) {
                count.setParameter("to", to);
                select.setParameter("to", to);
            }

            Long counter = count.getSingleResult();
            response.setTotal(counter == null ? 0L : counter);
            response.setConfirmInfoIds(select.getResultList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return response;
    }

    @Override
    public List<SubPartnerResponse> getConfirmInfoSubPartnerIdBy(String partnerId){
        boolean partnerFill = !StringUtils.isEmpty(partnerId);

        String selectQl = "select new vn.metech.dto.response.SubPartnerResponse( ci.subPartnerId, ci.subPartnerName)" +
                " from ConfirmInfo ci where ci.subPartnerId is not null " +
                (partnerFill ? " and ci.partnerId = :partnerId " : "") +
                " group by ci.subPartnerId, ci.subPartnerName ORDER BY ci.subPartnerName";
        TypedQuery<SubPartnerResponse> selectQuery = em.createQuery(selectQl, SubPartnerResponse.class);

        if (partnerFill) {
            selectQuery.setParameter("partnerId", partnerId);
        }

        return selectQuery.getResultList();
    }


    @Override
    public PageResponse<ConfirmInfo> getConfirmInfoBy(ConfirmRequest confirmRequest, PageRequest page) {
        boolean statusFill = !StringUtils.isEmpty(confirmRequest.getStatus());
        boolean serviceTypeFill = confirmRequest.getServiceType() != null;
        boolean partnerFill = !StringUtils.isEmpty(confirmRequest.getPartnerId());
        boolean subPartnerFill = !StringUtils.isEmpty(confirmRequest.getSubPartnerId());
        boolean phoneNumberFill = !StringUtils.isEmpty(confirmRequest.getPhoneNumber());
        boolean fromDateFill = confirmRequest.getFromDate() != null;
        boolean toDateFill = confirmRequest.getToDate() != null;

        PageResponse<ConfirmInfo> res = new PageResponse<>();
        res.setCurrentPage(page.getCurrentPage());
        res.setPageSize(page.getPageSize());
        try {

            String countQL = "select count(ci.id) from " + clazz.getName() +
                    " ci where " + " ci.statusCode is not null " +
                    (statusFill ? " and ci.status like :status " : "") +
                    (serviceTypeFill ? " and ci.serviceType = :serviceType " : "") +
                    (partnerFill ? " and ci.partnerId = :partnerId " : "") +
                    (subPartnerFill ? " and ci.subPartnerId = :subPartnerId " : "") +
                    (phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                    (fromDateFill ? "AND :fromDate <= createdDate " : "") +
                    (toDateFill ? "AND :toDate >= createdDate " : "") //
                    ;
            String selectQl = "from " + clazz.getName() +
                    " ci where " + " ci.statusCode is not null " +
                    (statusFill ? " and ci.status like :status " : "") + //
                    (serviceTypeFill ? " and ci.serviceType = :serviceType " : "") + //
                    (partnerFill ? " and ci.partnerId = :partnerId " : "") + //
                    (subPartnerFill ? " and ci.subPartnerId = :subPartnerId " : "") +
                    (phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                    (fromDateFill ? "AND :fromDate <= createdDate " : "") +
                    (toDateFill ? "AND :toDate >= createdDate " : "") +
                    " order by ci.createdDate ";
            TypedQuery<Long> countQuery = em.createQuery(countQL, Long.class);
            TypedQuery<ConfirmInfo> selectQuery = em.createQuery(selectQl, clazz);

            if (statusFill) {
                countQuery.setParameter("status", confirmRequest.getStatus());
                selectQuery.setParameter("status", confirmRequest.getStatus());
            }

            if (serviceTypeFill) {
                countQuery.setParameter("serviceType", confirmRequest.getServiceType());
                selectQuery.setParameter("serviceType", confirmRequest.getServiceType());
            }
            if (partnerFill) {
                countQuery.setParameter("partnerId", confirmRequest.getPartnerId());
                selectQuery.setParameter("partnerId", confirmRequest.getPartnerId());
            }

            if (subPartnerFill) {
                countQuery.setParameter("subPartnerId", confirmRequest.getSubPartnerId());
                selectQuery.setParameter("subPartnerId", confirmRequest.getSubPartnerId());
            }
            if (phoneNumberFill) {
                selectQuery.setParameter("phoneNumber", "%" + confirmRequest.getPhoneNumber() + "%");
                countQuery.setParameter("phoneNumber", "%" + confirmRequest.getPhoneNumber() + "%");
            }
            if (fromDateFill) {
                selectQuery.setParameter("fromDate", confirmRequest.getFromDate());
                countQuery.setParameter("fromDate", confirmRequest.getFromDate());
            }
            if (toDateFill) {
                selectQuery.setParameter("toDate", confirmRequest.getToDate());
                countQuery.setParameter("toDate", confirmRequest.getToDate());
            }

            res.setTotal(countQuery.getSingleResult());
            res.setData(selectQuery.setFirstResult(page.skip()).setMaxResults(page.getPageSize()).getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<ConfirmInfo> getConfirmInfoByBy(ConfirmRequest confirmRequest, PageRequest page) {

        boolean statusFill = !StringUtils.isEmpty(confirmRequest.getStatus());
        boolean serviceTypeFill = confirmRequest.getServiceType() != null;
        boolean partnerFill = !StringUtils.isEmpty(confirmRequest.getPartnerId());
        boolean subPartnerFill = !StringUtils.isEmpty(confirmRequest.getSubPartnerId());
        boolean phoneNumberFill = !StringUtils.isEmpty(confirmRequest.getPhoneNumber());
        boolean fromDateFill = confirmRequest.getFromDate() != null;
        boolean toDateFill = confirmRequest.getToDate() != null;

        try {

            String countQL = "select count(ci.id) from " + clazz.getName() +
                    " ci where " + " ci.statusCode is not null " +
                    (statusFill ? " and ci.status like :status " : "") + //
                    (serviceTypeFill ? " and ci.serviceType = :serviceType " : "") + //
                    (partnerFill ? " and ci.partnerId = :partnerId " : "") + //
                    (subPartnerFill ? " and ci.subPartnerId = :subPartnerId " : "") +
                    (phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                    (fromDateFill ? "AND :fromDate <= createdDate " : "") +
                    (toDateFill ? "AND :toDate >= createdDate " : "") //
                    ;
            String selectQl = "from " + clazz.getName() +
                    " ci where " + " ci.statusCode is not null " +
                    (statusFill ? " and ci.status like :status " : "") + //
                    (serviceTypeFill ? " and ci.serviceType = :serviceType " : "") + //
                    (partnerFill ? " and ci.partnerId = :partnerId " : "") + //
                    (subPartnerFill ? " and ci.subPartnerId = :subPartnerId " : "") +
                    (phoneNumberFill ? "AND phoneNumber LIKE :phoneNumber " : "") +
                    (fromDateFill ? "AND :fromDate <= createdDate " : "") +
                    (toDateFill ? "AND :toDate >= createdDate " : "") +
                    " order by ci.createdDate ";
            TypedQuery<Long> countQuery = em.createQuery(countQL, Long.class);
            TypedQuery<ConfirmInfo> selectQuery = em.createQuery(selectQl, clazz);

            if (statusFill) {
                countQuery.setParameter("status", confirmRequest.getStatus());
                selectQuery.setParameter("status", confirmRequest.getStatus());
            }

            if (serviceTypeFill) {
                countQuery.setParameter("serviceType", confirmRequest.getServiceType());
                selectQuery.setParameter("serviceType", confirmRequest.getServiceType());
            }
            if (partnerFill) {
                countQuery.setParameter("partnerId", confirmRequest.getPartnerId());
                selectQuery.setParameter("partnerId", confirmRequest.getPartnerId());
            }

            if (subPartnerFill) {
                countQuery.setParameter("subPartnerId", confirmRequest.getSubPartnerId());
                selectQuery.setParameter("subPartnerId", confirmRequest.getSubPartnerId());
            }
            if (phoneNumberFill) {
                selectQuery.setParameter("phoneNumber", "%" + confirmRequest.getPhoneNumber() + "%");
                countQuery.setParameter("phoneNumber", "%" + confirmRequest.getPhoneNumber() + "%");
            }
            if (toDateFill) {
                selectQuery.setParameter("toDate", confirmRequest.getToDate());
                countQuery.setParameter("toDate", confirmRequest.getToDate());
            }
            if (fromDateFill) {
                selectQuery.setParameter("fromDate", confirmRequest.getFromDate());
                countQuery.setParameter("fromDate", confirmRequest.getFromDate());
            }

            return selectQuery.setFirstResult(page.skip()).setMaxResults(page.getPageSize()).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PageResponse<ConfirmInfoResponse> getFilterRequest(FilterRequest filter, PageRequest page) {
        boolean subPartnerFill = !StringUtils.isEmpty(filter.getSubPartnerId());
        boolean fromDateFill = filter.getFromDate() != null;
        boolean toDateFill = filter.getToDate() != null;

        PageResponse<ConfirmInfoResponse> res = new PageResponse<>();
        res.setCurrentPage(page.getCurrentPage());
        res.setPageSize(page.getPageSize());

        try {

            String selectQl = "select new vn.metech.dto.response.ConfirmInfoResponse(ci.partnerName, ci.subPartnerName, count (case when ci.statusCode = 0 then 1 else null end), " +
                    " count (case when ci.statusCode = 1 then 1 else null end), count (ci.statusCode), CAST(ci.createdDate as date)) " +
                    " from " + " ConfirmInfo ci " + " where ci.status is not null " +
                    (subPartnerFill ? " and ci.subPartnerId = :subPartnerId " : "") +
                    (fromDateFill ? " AND :fromDate <= ci.createdDate " : "") +
                    (toDateFill ? " AND :toDate >= ci.createdDate " : "") +
                    " group by ci.partnerName, ci.subPartnerName, CAST(ci.createdDate as date)" +
                    " order by CAST(ci.createdDate as date)";
            TypedQuery<ConfirmInfoResponse> selectQuery = em.createQuery(selectQl, ConfirmInfoResponse.class);

            if (subPartnerFill) {
                selectQuery.setParameter("subPartnerId", filter.getSubPartnerId());
            }

            if (toDateFill) {
                selectQuery.setParameter("toDate", filter.getToDate());
            }
            if (fromDateFill) {
                selectQuery.setParameter("fromDate", filter.getFromDate());
            }

            res.setTotal(selectQuery.getResultList().size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<ConfirmInfoResponse> getFilterRequestBy(FilterRequest filter, PageRequest page) {
        boolean subPartnerFill = !StringUtils.isEmpty(filter.getSubPartnerId());
        boolean fromDateFill = filter.getFromDate() != null;
        boolean toDateFill = filter.getToDate() != null;

        try {

            String selectQl = "select new vn.metech.dto.response.ConfirmInfoResponse (ci.partnerName, ci.subPartnerName, count (case when ci.statusCode = 0 then 1 else null end)," +
                    " count (case when ci.statusCode <> 0 then 1 else null end), count (ci.statusCode), CAST(ci.createdDate as date))" +
                    " from " + " ConfirmInfo ci " + " where ci.status is not null " +
                    (subPartnerFill ? " and ci.subPartnerId = :subPartnerId " : "") +
                    (fromDateFill ? " AND :fromDate <= ci.createdDate " : "") +
                    (toDateFill ? " AND :toDate >= ci.createdDate " : "") +
                    " group by ci.partnerName, ci.subPartnerName, CAST(ci.createdDate as date)" +
                    " order by CAST(ci.createdDate as date)";
            TypedQuery<ConfirmInfoResponse> selectQuery = em.createQuery(selectQl, ConfirmInfoResponse.class);

            if (subPartnerFill) {
                selectQuery.setParameter("subPartnerId", filter.getSubPartnerId());
            }

            if (toDateFill) {
                selectQuery.setParameter("toDate", filter.getToDate());
            }
            if (fromDateFill) {
                selectQuery.setParameter("fromDate", filter.getFromDate());
            }

            return selectQuery.setFirstResult(page.skip()).setMaxResults(page.getPageSize()).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
