package vn.metech.dto.response;

import vn.metech.entity.User;
import vn.metech.util.StringUtils;

public class UserResponseAuth {

	private String userId;
	private String username;
	private String defaultCustomerCode;
	private String partnerId;
	private String partnerName;
	private String subPartnerId;
	private String subPartnerName;
	private Integer requestValidInDays;
	private String account;
	private String secureKey;
	private Boolean isAdmin;

	public UserResponseAuth() {
	}

	public UserResponseAuth(User user) {
		this();
		this.userId = user.getId();
		this.username = user.getUsername();
		this.defaultCustomerCode = user.getDefaultCustomerCode();
		this.partnerId = user.getPartnerId();
		this.partnerName = user.getPartner().getName();
		this.subPartnerId = user.getSubPartnerId();
		this.isAdmin = user.isAdmin();
	}

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean admin) {
		isAdmin = admin;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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

	public Integer getRequestValidInDays() {
		if (requestValidInDays == null) {
			if (!StringUtils.isEmpty(defaultCustomerCode)) {
				if (defaultCustomerCode.equalsIgnoreCase("VPB")) {
					return 30;
				}
				if (defaultCustomerCode.equalsIgnoreCase("TPC")) {
					return 7;
				}
			}
		}

		return requestValidInDays;
	}

	public void setRequestValidInDays(Integer requestValidInDays) {
		this.requestValidInDays = requestValidInDays;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public String getSubPartnerName() {
		return subPartnerName;
	}

	public void setSubPartnerName(String subPartnerName) {
		this.subPartnerName = subPartnerName;
	}
}
