package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.UserMapping;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IUserMappingRepository;

import javax.persistence.EntityManager;

@Repository
public class UserMappingRepositoryImpl extends RepositoryImpl<UserMapping> implements IUserMappingRepository {

	public UserMappingRepositoryImpl(EntityManager entityManager) {
		super(entityManager, UserMapping.class);
	}

//	@Override
//	public List<UserMapping> getRefUsersBy(String userId) {
//		try {
//			return entityManager.createQuery(
//							"from " + clazz.getName() + " where userId = :userId ", clazz)
//							.setParameter("userId", userId).getResultList();
//		} catch (Exception e) {
//			return Collections.emptyList();
//		}
//	}
//
//	@Override
//	public List<UserMapping> findUserWithMappingsBy(String keyword) {
//		try {
//			if (StringUtils.isEmpty(keyword)) {
//				return Collections.emptyList();
//			}
//			return entityManager.createQuery(
//							"from " + clazz.getName() + " where (userId = :userId or email like :keyword)", clazz)
//							.setParameter("userId", keyword)
//							.setParameter("keyword", "%" + keyword + "%").getResultList();
//		} catch (Exception e) {
//			return Collections.emptyList();
//		}
//	}
//
//	@Override
//	public long countUserMappingBy(String userId, String refUserId) {
//		try {
//			return entityManager.createQuery(
//							"select count(id) from " + clazz.getName() + " where userId = :userId and refUserId = :refUserId ",
//							Long.class).setParameter("userId", userId).setParameter("refUserId", refUserId).getSingleResult();
//		} catch (Exception e) {
//			return 0;
//		}
//	}
//
//	@Override
//	public UserMapping getUserMappingBy(String userId, String refUserId) {
//		try {
//			return entityManager.createQuery(
//							"from " + clazz.getName() + " where userId = :userId and refUserId = :refUserId ", clazz)
//							.setParameter("userId", userId).setParameter("refUserId", refUserId).setMaxResults(1).getSingleResult();
//		} catch (Exception e) {
//			return null;
//		}
//	}
}
