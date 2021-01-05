package vn.metech.service;

import vn.metech.dto.response.RoleListResponse;

import java.util.List;

public interface IRoleService {
  List<RoleListResponse> getAllRoles();
}
