package vn.metech.dto.response;

import java.util.ArrayList;
import java.util.List;

public class UserRoleResponse {

  private String userId;
  private String username;
  private List<String> apiPaths;

  public UserRoleResponse() {
    this.apiPaths = new ArrayList<>();
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getApiPaths() {
    return apiPaths;
  }

  public void setApiPaths(List<String> apiPaths) {
    this.apiPaths = apiPaths;
  }
}
