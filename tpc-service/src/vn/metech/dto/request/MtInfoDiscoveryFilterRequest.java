package vn.metech.dto.request;

import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.shared.PagedRequest;

import java.util.Date;

public class MtInfoDiscoveryFilterRequest extends PagedRequest {

	private String idNumber;
	private Date fromDate;
	private Date toDate;

	public MtInfoDiscoveryFilterRequest() {
		this.idNumber = "";
		this.toDate = new Date();
	}

	public MtInfoDiscoveryFilter toFilter() {
		MtInfoDiscoveryFilter filter = new MtInfoDiscoveryFilter();
		filter.setFromDate(fromDate);
		filter.setToDate(toDate);
		filter.setIdNumber(idNumber);
		filter.setPageSize(pageSize);
		filter.setCurrentPage(currentPage);

		return filter;
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
