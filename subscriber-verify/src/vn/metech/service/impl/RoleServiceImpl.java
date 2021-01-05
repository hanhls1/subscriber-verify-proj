package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import vn.metech.dto.response.RoleListResponse;
import vn.metech.repository.IRoleRepository;
import vn.metech.repository.jpa.RoleCrudRepository;
import vn.metech.service.IRoleService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

  private IRoleRepository roleRepository;
  private RoleCrudRepository roleCrudRepository;

  public RoleServiceImpl(
      IRoleRepository roleRepository, RoleCrudRepository roleCrudRepository) {
    this.roleRepository = roleRepository;
    this.roleCrudRepository = roleCrudRepository;
  }

  @Override
  public List<RoleListResponse> getAllRoles() {
    return RoleListResponse.fromCollection(roleCrudRepository.findAll());
  }
}
