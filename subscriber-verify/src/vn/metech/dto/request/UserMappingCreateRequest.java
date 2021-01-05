package vn.metech.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserMappingCreateRequest {

	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	private String refUserId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRefUserId() {
		return refUserId;
	}

	public void setRefUserId(String refUserId) {
		this.refUserId = refUserId;
	}
}
