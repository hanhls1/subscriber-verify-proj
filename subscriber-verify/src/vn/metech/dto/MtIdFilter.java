package vn.metech.dto;

import vn.metech.shared.PagedRequest;
import vn.metech.util.StringUtils;

import java.util.Date;

public class MtIdFilter extends PagedRequest {

	private String phoneNumber;
	private Date fromDate;
	private Date toDate;

	public MtIdFilter() {
		this.phoneNumber = "";
		this.toDate = new Date();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
