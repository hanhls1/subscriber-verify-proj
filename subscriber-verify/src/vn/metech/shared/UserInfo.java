package vn.metech.shared;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

	private String userId;
	private String username;
	private String partnerId;
	private String partnerName;
	private String defaultCustomerCode;
	private String partnerRequestUrl;
	private String accountCode;
	private List<String> acceptPaths;

	public UserInfo() {
		acceptPaths = new ArrayList<>();
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

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getDefaultCustomerCode() {
		return defaultCustomerCode;
	}

	public void setDefaultCustomerCode(String defaultCustomerCode) {
		this.defaultCustomerCode = defaultCustomerCode;
	}

	public String getPartnerRequestUrl() {
		return partnerRequestUrl;
	}

	public void setPartnerRequestUrl(String partnerRequestUrl) {
		this.partnerRequestUrl = partnerRequestUrl;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public List<String> getAcceptPaths() {
		return acceptPaths;
	}

	public void setAcceptPaths(List<String> acceptPaths) {
		this.acceptPaths = acceptPaths;
	}
}
