package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.common.ServiceType;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.repository.IConfirmInfoReceiveRepository;
import vn.metech.repository.base.RepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ConfirmInfoReceiveRepositoryImpl
        extends RepositoryImpl<ConfirmInfoReceive> implements IConfirmInfoReceiveRepository {

    public ConfirmInfoReceiveRepositoryImpl(EntityManager em) {
        super(em, ConfirmInfoReceive.class);
    }

    @Override
    public ConfirmInfoReceive findByPhoneNumberAndServiceType(
            String phoneNumber, ServiceType serviceType, List<String> userIds) {
        try {
            return em.createQuery("select cir from " + clazz.getName() + " cir " +
                    "left join fetch cir.confirmInfo ci left join fetch ci.params " +
                    "where ci is not null " +
                    "and cir.phoneNumber = :phoneNumber and cir.serviceType = :serviceType " +
                    "and cir.createdBy in (:userIds) order by cir.createdDate desc", clazz)
                    .setParameter("phoneNumber", phoneNumber).setParameter("serviceType", serviceType)
                    .setParameter("userIds", userIds).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ConfirmInfoReceive findByRequestId(String requestId, List<String> userIds) {
        try {
            return em.createQuery("select cir from " + clazz.getName() + " cir " +
                    "left join fetch cir.confirmInfo ci left join fetch ci.params " +
                    "where ci is not null and ci.requestId = :requestId " +
                    "and cir.createdBy in (:userIds) order by cir.createdDate desc", clazz)
                    .setParameter("requestId", requestId)
                    .setParameter("userIds", userIds).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
