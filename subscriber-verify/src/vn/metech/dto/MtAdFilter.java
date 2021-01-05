package vn.metech.dto;

import vn.metech.constant.RequestStatus;
import vn.metech.constant.VerifyService;
import vn.metech.shared.PagedRequest;
import vn.metech.util.StringUtils;

import java.util.Date;

public class MtAdFilter extends PagedRequest {

	private RequestStatus requestStatus;
	private String phoneNumber;
	private Date fromDate;
	private Date toDate;
	private VerifyService serviceType;

	public MtAdFilter() {
		this.phoneNumber = "";
		this.toDate = new Date();
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
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

	public VerifyService getServiceType() {
		return serviceType;
	}

	public void setServiceType(VerifyService serviceType) {
		this.serviceType = serviceType;
	}
}
