package vn.metech.dto.request;

import vn.metech.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserUpdateRequest {

	private Boolean agency;

	private Boolean admin;

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@Email
	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String subPartnerId;

	@NotNull
	@NotEmpty
	private String phoneNumber;

	@NotNull
	@NotEmpty
	private String userId;
	private Boolean activated;
	private Boolean locked;
	private String defaultCustomerCode;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getDefaultCustomerCode() {
		return defaultCustomerCode;
	}

	public void setDefaultCustomerCode(String defaultCustomerCode) {
		this.defaultCustomerCode = defaultCustomerCode;
	}

	public Boolean getAgency() {
		return agency == null ? false : agency;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubPartnerId() {
		return subPartnerId;
	}

	public void setSubPartnerId(String subPartnerId) {
		this.subPartnerId = subPartnerId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User toUser(User user) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setActivated(activated);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setLocked(locked);
		user.setAgency(getAgency());
		return user;
	}

}
