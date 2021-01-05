package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserListResponse implements Serializable {

	private String userId;
	private String username;
	private String partnerCode;
	private String email;
	private Boolean locked;
	private Boolean activated;
	private Boolean agency;
	private Boolean admin;

	protected void setProperties(User user) {
		this.userId = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.locked = user.isLocked();
		this.activated = user.isActivated();
		this.agency = user.isAgency();
		this.admin = user.isAdmin();
		this.partnerCode = user.getPartner().getPartnerCode();
	}

	public static UserListResponse from(User user) {
		if (user == null) {
			return null;
		}

		UserListResponse userListResponse = new UserListResponse();
		userListResponse.setProperties(user);

		return userListResponse;
	}

	public static List<UserListResponse> fromCollection(Collection<User> users) {
		if (users == null || users.isEmpty()) {
			return Collections.emptyList();
		}

		return users.stream().map(UserListResponse::from).collect(Collectors.toList());
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

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getAgency() {
		return agency;
	}

	public void setAgency(Boolean agency) {
		this.agency = agency;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
}
