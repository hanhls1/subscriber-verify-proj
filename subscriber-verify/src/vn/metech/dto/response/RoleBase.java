package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.Role;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleBase implements Serializable {

  protected String roleId;
  protected String roleName;
  protected String roleUri;

  protected void setProperties(Role role) {
    this.roleId = role.getId();
    this.roleName = role.getName();
    this.roleUri = role.getUri();
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleUri() {
    return roleUri;
  }

  public void setRoleUri(String roleUri) {
    this.roleUri = roleUri;
  }
}
