package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.AdResponse;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IAdResponseRepository;

import javax.persistence.EntityManager;

@Repository
public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               AdResponseRepositoryImpl
				extends RepositoryImpl<AdResponse> implements IAdResponseRepository {

	public AdResponseRepositoryImpl(EntityManager entityManager) {
		super(entityManager, AdResponse.class);
	}

//	@Override
//	public AdResponse getResponseIncludeRequestsBy(String responseId, ResponseStatus... statuses) {
//		// @formatter:off
//		String jpql =
//				"SELECT ares " +
//				"FROM " + clazz.getName() + " ares " +
//					"LEFT JOIN FETCH ares.adRequests areq " +
//				"WHERE ares.deleted = :deleted " +
//          "AND areq.deleted = :deleted " +
//					"AND ares.id = :responseId " +
//					"AND ares.responseStatus IN (:statuses)";
//		// @formatter:on
//		try {
//			return entityManager
//							.createQuery(jpql, clazz)
//							.setParameter("deleted", false)
//							.setParameter("responseId", responseId)
//							.setParameter("statuses", Arrays.asList(statuses))
//							.setMaxResults(1)
//							.getSingleResult();
//		} catch (Exception ignored) {
//			return null;
//		}
//	}
//
//	@Override
//	public void updateSubscriberStatus(String requestId, SubscriberStatus subscriberStatus) {
//		try {
//			entityManager.createQuery(
//							"update " + clazz.getName() + " set subscriberStatus = :subscriberStatus where id = :id")
//							.setParameter("subscriberStatus", subscriberStatus).setParameter("id", requestId).executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void updateMbfStatus(String requestId, MbfStatus mbfStatus) {
//		try {
//			entityManager.createQuery(
//							"update " + clazz.getName() + " set mbfStatus = :mbfStatus where id = :id"
//			).setParameter("mbfStatus", mbfStatus).setParameter("id", requestId).executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void updateResponseData(String id, String jsonData, ResponseStatus responseStatus) {
//		try {
//			entityManager.createQuery(
//							"update " + clazz.getName() +
//											" set responseData = :jsonData, responseStatus = :responseStatus where id = :id")
//							.setParameter("jsonData", jsonData).setParameter("id", id)
//							.setParameter("responseStatus", responseStatus).executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
