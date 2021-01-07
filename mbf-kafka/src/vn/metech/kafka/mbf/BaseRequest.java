package vn.metech.kafka.mbf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.util.StringUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class BaseRequest {

	@JsonProperty("account")
	protected String accountCode;

	@JsonProperty("secureCode")
	protected String secureCode;

	@JsonProperty("customerCode")
	private String customerCode;

	@JsonProperty("requestId")
	private String partnerRequestId;

	@JsonProperty("phonenumber")
	private String phoneNumber;

	@JsonProperty("qRequestId")
	private String acceptedRequestId;

	public BaseRequest() {
	}

	public BaseRequest(String customerCode, String phoneNumber) {
		this.customerCode = customerCode;
		this.phoneNumber = phoneNumber;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getSecureCode() {
		return secureCode;
	}

	public void setSecureCode(String secureCode) {
		this.secureCode = secureCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPartnerRequestId() {
		return partnerRequestId;
	}

	public void setPartnerRequestId(String partnerRequestId) {
		this.partnerRequestId = partnerRequestId;
	}

	public String getPhoneNumber() {
		if (!StringUtils.isEmpty(phoneNumber)) {
			return phoneNumber.replaceAll("[^0-9]", "");
		}
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.trim().replaceAll("[^0-9]", "");
		}
	}

	public String getAcceptedRequestId() {
		return acceptedRequestId;
	}

	public void setAcceptedRequestId(String acceptedRequestId) {
		this.acceptedRequestId = acceptedRequestId;
	}
}
