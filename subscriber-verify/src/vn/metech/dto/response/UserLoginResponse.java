package vn.metech.dto.response;

import java.util.List;

public class UserLoginResponse {

	private String token;
	private String userId;
	private String username;
	private String partnerCode;
	private List<String> apiPaths;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public List<String> getApiPaths() {
		return apiPaths;
	}

	public void setApiPaths(List<String> apiPaths) {
		this.apiPaths = apiPaths;
	}
}
