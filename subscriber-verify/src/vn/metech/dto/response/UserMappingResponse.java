package vn.metech.dto.response;

import vn.metech.entity.User;
import vn.metech.entity.UserMapping;

import java.util.ArrayList;
import java.util.List;

public class UserMappingResponse {

	private String userId;
	private String email;
	private List<RefUserResponse> refUsers;

	public UserMappingResponse() {
		this.refUsers = new ArrayList<>();
	}

	public UserMappingResponse(String userId, String email) {
		this();
		this.userId = userId;
		this.email = email;
	}

	public UserMappingResponse(User user) {
		this(user.getId(), user.getEmail());
	}

	public UserMappingResponse applyRefUsers(List<UserMapping> userMappings) {
		if (userMappings != null && !userMappings.isEmpty()) {
			this.refUsers = new ArrayList<>();
			for (UserMapping userMapping : userMappings) {
				addRefUser(userMapping);
			}
		}

		return this;
	}

	public void addRefUser(UserMapping userMapping) {
		if (userMapping == null) return;
		this.refUsers.add(new RefUserResponse(userMapping));
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RefUserResponse> getRefUsers() {
		return refUsers;
	}

	public void setRefUsers(List<RefUserResponse> refUsers) {
		this.refUsers = refUsers;
	}
}
