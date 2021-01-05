package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.Role;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IRoleRepository;

import javax.persistence.EntityManager;

@Repository
public class RoleRepositoryImpl extends RepositoryImpl<Role> implements IRoleRepository {

  public RoleRepositoryImpl(EntityManager entityManager) {
    super(entityManager, Role.class);
  }

//  @Override
//  public List<Role> getAllActivatedRoles() {
//    // @formatter:off
//    String jpql = "FROM " + clazz.getName() + " " +
//                  "WHERE deleted = :deleted";
//    // @formatter:on
//    try {
//      return entityManager
//          .createQuery(jpql, clazz)
//          .setParameter("deleted", false)
//          .getResultList();
//    } catch (Exception e) {
//      return Collections.emptyList();
//    }
//  }
//
//  @Override
//  public List<Role> getRolesByIds(List<String> roleIds) {
//    if (roleIds == null || roleIds.isEmpty()) {
//      return Collections.emptyList();
//    }
//    // @formatter:off
//    String jpql = "FROM " + clazz.getName() + " " +
//                  "WHERE deleted = :deleted " +
//                  "AND id IN (:ids) ";
//    // @formatter:on
//    try {
//      return entityManager
//          .createQuery(jpql, clazz)
//          .setParameter("ids", roleIds)
//          .setParameter("deleted", false)
//          .getResultList();
//    } catch (Exception e) {
//      return Collections.emptyList();
//    }
//  }
}
