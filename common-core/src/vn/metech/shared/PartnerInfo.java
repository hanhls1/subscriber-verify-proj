package vn.metech.shared;

import vn.metech.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PartnerInfo {

	private String url;
	private String username;
	private String password;
	private List<String> customerCodes = new ArrayList<>();

	public PartnerInfo() {
	}

	public PartnerInfo(String url, String username, String password) {
		this();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public PartnerInfo(String url, String username, String password, List<String> customerCodes) {
		this(url, username, password);
		this.customerCodes = customerCodes;
	}

	public boolean existCustomerCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return false;
		}

		for (String customerCode : customerCodes) {
			if (code.equalsIgnoreCase(customerCode)) {
				return true;
			}
		}

		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public List<String> getCustomerCodes() {
		return customerCodes;
	}

	public void setCustomerCodes(List<String> customerCodes) {
		this.customerCodes = customerCodes;
	}
}
