package vn.metech.dto.request;

import vn.metech.dto.MtIdFilter;
import vn.metech.shared.PagedRequest;
import vn.metech.util.StringUtils;

import java.util.Date;

public class MtIdFilterRequest extends PagedRequest {

	private String phoneNumber;
	private Date fromDate;
	private Date toDate;

	public MtIdFilter toFilter() {
		MtIdFilter filter = new MtIdFilter();
		filter.setPhoneNumber(phoneNumber);
		if (fromDate != null) {
			filter.setFromDate(fromDate);
		}
		if (toDate != null) {
			filter.setToDate(toDate);
		}
		filter.setPageSize(pageSize);
		filter.setCurrentPage(currentPage);

		return filter;
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
