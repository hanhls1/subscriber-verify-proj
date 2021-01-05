package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleListResponse extends RoleBase {

  private String roleGroupId;
  private String roleGroupName;

  public static RoleListResponse from (Role role) {
    RoleListResponse roleListResponse = new RoleListResponse();
    roleListResponse.setProperties(role);
    roleListResponse.roleGroupId = role.getRoleGroupId();
    roleListResponse.roleGroupName = role.getRoleGroupName();

    return roleListResponse;
  }

  public static List<RoleListResponse> fromCollection(Collection<Role> roles) {
    if (roles == null || roles.isEmpty()) {
      return Collections.emptyList();
    }

    return roles.stream().map(RoleListResponse::from).collect(Collectors.toList());
  }

  public String getRoleGroupId() {
    return roleGroupId;
  }

  public void setRoleGroupId(String roleGroupId) {
    this.roleGroupId = roleGroupId;
  }

  public String getRoleGroupName() {
    return roleGroupName;
  }

  public void setRoleGroupName(String roleGroupName) {
    this.roleGroupName = roleGroupName;
  }
}
