package vn.metech.dto.request;

import vn.metech.constant.Regex;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class MtIdRequest {

	@NotNull
	private String requestId;

	private String customerCode;

	@NotNull
	@Pattern(regexp = Regex.PHONE_NUMBER)
	private String phoneNumber;

	@NotNull
	@NotEmpty
	private String idNumber;

	@NotNull
	private Date dateToCheck;

	public MtIdRequest() {
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
}
