package vn.metech.dto;

public class RequestWithKey {

	private String customerCode;
	private String account;
	private String secureKey;

	public RequestWithKey() {
	}

	public RequestWithKey(String customerCode, String account, String secureKey) {
		this.customerCode = customerCode;
		this.account = account;
		this.secureKey = secureKey;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getAccount() {
		return account;
	}

	public String getSecureKey() {
		return secureKey;
	}
}
