package vn.metech.dto.request;

import java.util.ArrayList;
import java.util.List;

public class UserRoleUpdateRequest {

  private String userId;
  private List<String> roleIds;

  public UserRoleUpdateRequest() {
    this.roleIds = new ArrayList<>();
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<String> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(List<String> roleIds) {
    this.roleIds = roleIds;
  }
}
