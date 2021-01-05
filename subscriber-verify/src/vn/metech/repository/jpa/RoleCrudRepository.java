package vn.metech.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.Role;

import java.util.List;

public interface RoleCrudRepository extends CrudRepository<Role, String> {

    List<Role> findAll();

    List<Role> findAllByIdIn(List<String> roleIds);
}
