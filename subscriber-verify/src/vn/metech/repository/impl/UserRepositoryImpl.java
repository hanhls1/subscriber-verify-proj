package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.dto.request.UserFilterPagedRequest;
import vn.metech.entity.User;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IUserRepository;
import vn.metech.shared.PagedResult;
import vn.metech.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl
				extends RepositoryImpl<User> implements IUserRepository {

	public UserRepositoryImpl(EntityManager entityManager) {
		super(entityManager, User.class);
	}

	@Override
	public User getById(String userId) {
		try {
			User user = entityManager.find(clazz, userId);
			return user.isDeleted() ? null : user;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}


	@Override
	public User getActiveAgentUserById(String userId) {
		try {
			// @formatter:off
            String jpql =
                    " SELECT " +
                        "u" +
                    " FROM " +
                        clazz.getName() + " u " +
                    " WHERE " +
                        "u.deleted = :deleted" +
                            " AND " +
                        "u.locked = :locked" +
                            " AND " +
                        "u.activated = :activated" +
				                    " AND " +
		                    "u.id = :userId";
            // @formatter:on
			return entityManager
							.createQuery(jpql, clazz)
							.setParameter("userId", userId)
							.setParameter("deleted", false)
							.setParameter("locked", false)
							.setParameter("activated", true)
							.setMaxResults(1)
							.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PagedResult<User> getUpdatableUsersBy(UserFilterPagedRequest userFilterPagedRequest, String agentId) {
		boolean agentFill = !StringUtils.isEmpty(agentId);
		boolean keywordFill = !StringUtils.isEmpty(userFilterPagedRequest.getKeyword());
		try {
			// @formatter:off
			String countJpql = "SELECT COUNT(*) " +
													"FROM " + clazz.getName() + " " +
													"WHERE deleted = :deleted " +
													(keywordFill ? "AND (username LIKE :keyword OR email LIKE :keyword)" : "");
			String jpql = "SELECT u " +
										"FROM " + clazz.getName() + " u " +
											"LEFT JOIN FETCH u.partner p " +
										"WHERE u.deleted = :deleted " +
											(keywordFill ? "AND (u.username LIKE :keyword OR u.email LIKE :keyword)" : "");
			User user = null;
			if (agentFill) {
				user = getActiveAgentUserById(agentId);
				if (user == null || (StringUtils.isEmpty(user.getPartnerId()) && !user.isAdmin())) {
					return new PagedResult<>();
				}

				if (user.isAgency() && !user.isAdmin()) {
					jpql += " AND partnerId = :partnerId ";
					countJpql += " AND partnerId = :partnerId ";
				}

				if (!user.isAdmin()) {
					jpql += " AND u.updatable = :updatable ";
					countJpql += " AND updatable = :updatable ";
				}
			}
			// @formatter:on
			TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class).setParameter("deleted", false);
			TypedQuery<User> userQuery = entityManager.createQuery(jpql, clazz).setParameter("deleted", false);
			if (agentFill && user != null) {
				if (user.isAgency() && !user.isAdmin()) {
					userQuery.setParameter("partnerId", user.getPartnerId());
					countQuery.setParameter("partnerId", user.getPartnerId());
				}

				if (!user.isAdmin()) {
					userQuery.setParameter("updatable", true);
					countQuery.setParameter("updatable", true);
				}
			}

			if (keywordFill) {
				userQuery.setParameter("keyword", "%" + userFilterPagedRequest.getKeyword() + "%");
				countQuery.setParameter("keyword", "%" + userFilterPagedRequest.getKeyword() + "%");
			}
			List<User> users = userQuery
							.setFirstResult(userFilterPagedRequest.skip())
							.setMaxResults(userFilterPagedRequest.take())
							.getResultList();
			return new PagedResult<>(countQuery.getSingleResult().intValue(), users);
		} catch (Exception e) {
			return new PagedResult<>();
		}
	}

}
