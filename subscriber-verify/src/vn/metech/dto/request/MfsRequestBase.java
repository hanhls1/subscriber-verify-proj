package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import vn.metech.util.HashUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class MfsRequestBase implements Serializable {

	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("account")
	private String accountCode;

	@JsonProperty("customerCode")
	private String customerCode;

	@JsonProperty("secureCode")
	private String secureCode;

	@JsonProperty("phonenumber")
	private String phoneNumber;

	private MfsRequestBase() {
		this.phoneNumber = "";
	}

	protected MfsRequestBase(String accountCode, String customerCode, String hashKey) {
		this(accountCode, customerCode, "", hashKey);
	}

	protected MfsRequestBase(String accountCode, String customerCode, String phoneNumber, String hashKey) {
		this(StringUtils._3tId(), accountCode, customerCode, phoneNumber, hashKey);
	}

	protected MfsRequestBase(
					String requestId, String accountCode, String customerCode, String phoneNumber, String hashKey) {
		Assert.notNull(requestId, "requestId required");
		Assert.notNull(accountCode, "account required");
		Assert.notNull(customerCode, "customerCode required");
		Assert.notNull(hashKey, "hashKey required");
		this.requestId = requestId;
		this.accountCode = accountCode;
		this.customerCode = customerCode;
		this.phoneNumber = phoneNumber;
		this.secureCode = HashUtils.sha256(accountCode + "|" + phoneNumber + "|" + requestId + "|" + hashKey);
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getSecureCode() {
		return secureCode;
	}

	public void setSecureCode(String secureCode) {
		this.secureCode = secureCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
