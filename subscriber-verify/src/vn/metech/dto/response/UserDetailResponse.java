package vn.metech.dto.response;

import vn.metech.entity.User;

public class UserDetailResponse {

	private String userId;
	private String username;
	private String firstName;
	private String lastName;
	private Boolean agency;
	private Boolean locked;
	private String defaultCustomerCode;
	private String partnerId;
	private String subPartnerId;

	public UserDetailResponse(User user) {
		this.userId = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.agency = user.isAgency();
		this.locked = user.isLocked();
		this.defaultCustomerCode = user.getDefaultCustomerCode();
		this.partnerId = user.getPartnerId();
		this.subPartnerId = user.getSubPartnerId();
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

	public Boolean getAgency() {
		return agency;
	}

	public void setAgency(Boolean agency) {
		this.agency = agency;
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

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getSubPartnerId() {
		return subPartnerId;
	}

	public void setSubPartnerId(String subPartnerId) {
		this.subPartnerId = subPartnerId;
	}
}
