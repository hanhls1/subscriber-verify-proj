package vn.metech.repository;

import vn.metech.entity.UserRole;
import vn.metech.jpa.repository.IRepository;

public interface IUserRoleRepository extends IRepository<UserRole> {

	boolean isAccepted(String path, String userId);

}
