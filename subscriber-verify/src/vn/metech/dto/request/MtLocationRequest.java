package vn.metech.dto.request;

import vn.metech.constant.Regex;
import vn.metech.dto.MtLocation;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class MtLocationRequest {

	@NotNull
	private String requestId;

	private String customerCode;

	@NotNull
	@Pattern(regexp = Regex.PHONE_NUMBER)
	private String phoneNumber;

	@NotNull
	private Date dateToCheck;

	@NotNull
	private MtLocation workAddress;

	@NotNull
	private MtLocation homeAddress;

	private MtLocation referAddress;

	@NotNull
	private Integer totalDate;

	public MtLocationRequest() {
		totalDate = 90;
		requestId = StringUtils._3tId();
		referAddress = new MtLocation("", "0.0", "0.0", 0);
		dateToCheck = DateUtils.addMonth(new Date(), -1);
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

	public MtLocation getWorkAddress() {
		return (workAddress);
	}

	public void setWorkAddress(MtLocation workAddress) {
		this.workAddress = workAddress;
	}

	public MtLocation getHomeAddress() {
		return (homeAddress);
	}

	public void setHomeAddress(MtLocation homeAddress) {
		this.homeAddress = homeAddress;
	}

	public MtLocation getReferAddress() {
		return (referAddress);
	}

	public void setReferAddress(MtLocation referAddress) {
		this.referAddress = referAddress;
	}

	public Integer getTotalDate() {
		return totalDate;
	}

	public void setTotalDate(Integer totalDate) {
		this.totalDate = totalDate;
	}
}
