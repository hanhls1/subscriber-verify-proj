package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vn.metech.constant.Regex;
import vn.metech.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MtCallRequest {

	@JsonIgnore
	private Boolean basic;

	@NotNull
	private String requestId;

	private String customerCode;

	@NotNull
	@Pattern(regexp = Regex.PHONE_NUMBER)
	private String phoneNumber;

	@NotNull
	@Pattern(regexp = Regex.PHONE_NUMBER)
	private String refPhone1;

	@NotNull
	@Pattern(regexp = Regex.PHONE_NUMBER)
	private String refPhone2;

	public MtCallRequest() {
		requestId = StringUtils._3tId();
	}

	public boolean getBasic() {
		return basic == null ? false : basic;
	}

	public void setBasic(boolean basic) {
		this.basic = basic;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPhoneNumber() {
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			return this.phoneNumber.trim();
		}
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = this.phoneNumber.trim();
		}
	}

	public String getRefPhone1() {
		if (!StringUtils.isEmpty(this.refPhone1)) {
			return this.refPhone1.trim();
		}
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
		if (!StringUtils.isEmpty(this.refPhone1)) {
			this.refPhone1 = this.refPhone1.trim();
		}
	}

	public String getRefPhone2() {
		if (!StringUtils.isEmpty(this.refPhone2)) {
			return this.refPhone2.trim();
		}
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
		if (!StringUtils.isEmpty(this.refPhone2)) {
			this.refPhone2 = this.refPhone2.trim();
		}
	}

}
