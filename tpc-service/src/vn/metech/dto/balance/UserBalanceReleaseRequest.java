package vn.metech.dto.balance;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserBalanceReleaseRequest {

	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	private String requestId;

	public UserBalanceReleaseRequest() {
	}

	public UserBalanceReleaseRequest(String userId, String requestId) {
		this();
		this.userId = userId;
		this.requestId = requestId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
