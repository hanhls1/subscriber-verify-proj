package vn.metech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.metech.entity.UserService;

import java.util.List;

public interface UserServiceJpaRepository extends JpaRepository<UserService, String> {

	List<UserService> findAllByServiceType(String serviceType);

	List<UserService> findAllByUserId(String userId);

	UserService findOneByUserIdAndServiceType(String userId, String code);
}
