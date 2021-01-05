package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.dto.request.PageRequest;
import vn.metech.dto.response.PageResponse;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.repository.TpcConfirmInfoRepository;
import vn.metech.repository.base.RepositoryImpl;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class TpcConfirmInfoRepositoryImpl extends RepositoryImpl<TpcConfirmInfo> implements TpcConfirmInfoRepository {

    public TpcConfirmInfoRepositoryImpl(EntityManager em) {
        super(em, TpcConfirmInfo.class);
    }

    @Override
    public TpcConfirmInfo getAggregateTpcConfirmInfo(String phoneNumber, Date checkDate, Date duplicateBefore) {
        try {
            return em.createQuery(
                    "select tci from " + clazz.getName() + " tci where tci.phoneNumber = :phoneNumber and " +
                            "(tci.checkDate between :duplicateBefore and :checkDate)", clazz)
                    .setParameter("phoneNumber", phoneNumber).setParameter("checkDate", checkDate)
                    .setParameter("duplicateBefore", duplicateBefore).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PageResponse<TpcConfirmInfo> getTpcConfirmInfoBy(String phoneNumber, Date from, Date to, List<String> userIds, PageRequest page) {
        boolean phoneFill = !StringUtils.isEmpty(phoneNumber);
        boolean fromFill = from != null;
        boolean toFill = to != null;
        PageResponse<TpcConfirmInfo> res = new PageResponse<>();
        res.setCurrentPage(page.getCurrentPage());
        res.setPageSize(page.getPageSize());
        try {

            String countQL = "select count(ci.id) from " + clazz.getName() +
                    " ci where ci.createdBy in (:userIds) " +
                    (phoneFill ? " and ci.phoneNumber like :phoneNumber " : "") + //
                    (fromFill ? " and ci.createdDate >= :from " : "") + //
                    (toFill ? " and ci.createdDate <= :to " : "") //
                    ;
            String selectQl = "from " + clazz.getName() +
                    " ci where  ci.createdBy in (:userIds) " +
                    (phoneFill ? " and ci.phoneNumber like :phoneNumber " : "") + //
                    (fromFill ? " and ci.createdDate >= :from " : "") + //
                    (toFill ? " and ci.createdDate <= :to " : "") //
                    ;
            TypedQuery<Long> countQuery = em.createQuery(countQL, Long.class).setParameter("userIds", userIds);
            TypedQuery<TpcConfirmInfo> selectQuery = em.createQuery(selectQl, clazz).setParameter("userIds", userIds);

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
}
