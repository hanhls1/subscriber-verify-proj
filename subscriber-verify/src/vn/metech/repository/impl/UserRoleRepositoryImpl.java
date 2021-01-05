package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.UserRole;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IUserRoleRepository;

import javax.persistence.EntityManager;

@Repository
public class UserRoleRepositoryImpl
				extends RepositoryImpl<UserRole> implements IUserRoleRepository {

	public UserRoleRepositoryImpl(EntityManager entityManager) {
		super(entityManager, UserRole.class);
	}

	@Override
	public boolean isAccepted(String path, String userId) {
		try {
			// @formatter:off
            String jpql =
                    " SELECT " +
                            "uri" +
                    " FROM " +
                            clazz.getName() +
                    " WHERE " +
                        "deleted = :deleted" +
                            " AND " +
                        "(" +
                            "userId = :userId" +
                                " AND " +
                            ":requestPath LIKE CONCAT(uri, '%')" +
                        ")";
            // @formatter:on
			return entityManager
							.createQuery(jpql, String.class)
							.setParameter("deleted", false)
							.setParameter("userId", userId)
							.setParameter("requestPath", path)
							.setMaxResults(1)
							.getSingleResult() != null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}


}
