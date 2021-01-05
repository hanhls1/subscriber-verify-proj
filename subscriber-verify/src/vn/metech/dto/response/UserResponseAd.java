package vn.metech.dto.response;

public class UserResponseAd {

	private String userId;
	private String username;
	private String defaultCustomerCode;
	private String partnerId;
	private String subPartnerId;
	private String subPartnerName;

	public UserResponseAd() {
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

	public String getSubParnerName() {
		return subPartnerName;
	}

	public void setSubParnerName(String subParnerName) {
		this.subPartnerName = subParnerName;
	}
}
