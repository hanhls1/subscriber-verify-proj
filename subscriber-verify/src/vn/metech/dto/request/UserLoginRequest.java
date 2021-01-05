package vn.metech.dto.request;

import vn.metech.constant.PartnerConst;

import javax.validation.constraints.NotNull;

public class UserLoginRequest {

	@NotNull(message = "{user.username.not_empty}")

	private String username;
	@NotNull(message = "{user.password.not_empty}")
	private String password;

	private String partnerCode;

	public UserLoginRequest() {
		partnerCode = PartnerConst.DEFAULT_PARTNER_CODE;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
}
