package vn.metech.dto;

import vn.metech.shared.PagedRequest;

import java.util.Date;

public class MtInfoDiscoveryFilter extends PagedRequest {

	private String idNumber;
	private Date fromDate;
	private Date toDate;

	public MtInfoDiscoveryFilter() {
		this.idNumber = "";
		this.toDate = new Date();
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
