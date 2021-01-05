package vn.metech.dto.request;

import javax.validation.constraints.NotNull;

public class UserChangePasswordRequest {

	@NotNull
	private String currentPassword;

	@NotNull
	private String newPassword;

	@NotNull
	private String verifyNewPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getVerifyNewPassword() {
		return verifyNewPassword;
	}

	public void setVerifyNewPassword(String verifyNewPassword) {
		this.verifyNewPassword = verifyNewPassword;
	}
}
