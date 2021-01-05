package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.User;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserCreateResponse implements Serializable {

	private String userId;
	private String username;
	private String firstName;
	private String lastName;

	public UserCreateResponse() {
	}

	public UserCreateResponse(User user) {
		if (user == null) return;

		this.userId = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
