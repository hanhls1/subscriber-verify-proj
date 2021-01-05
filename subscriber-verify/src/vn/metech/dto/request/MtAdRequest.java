package vn.metech.dto.request;

import vn.metech.constant.Regex;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class MtAdRequest {

	@NotNull
	private String requestId;

	private String customerCode;

	@NotNull
	private String partnerId;

	@NotNull
	private String subPartnerName;

	@NotNull
	@Pattern(regexp = Regex.PHONE_NUMBER)
	private String phoneNumber;

	@NotNull
	private Date dateToCheck;

	public MtAdRequest() {
		requestId = StringUtils._3tId();
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

	public Date getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(Date dateToCheck) {
		if (dateToCheck != null) {
			String strDate = DateUtils.formatDate(dateToCheck, "yyyy-MM-dd");
			String nowStrDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			this.dateToCheck = strDate.equalsIgnoreCase(nowStrDate) ? DateUtils.addMonth(new Date(), -1) : dateToCheck;
		} else {
			this.dateToCheck = DateUtils.addMonth(new Date(), -1);
		}
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getSubPartnerName() {
		return subPartnerName;
	}

	public void setSubPartnerName(String subPartnerName) {
		this.subPartnerName = subPartnerName;
	}
}
