package vn.metech.dto.request;

import vn.metech.dto.MtLocationFilter;
import vn.metech.shared.PagedRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.util.Date;

public class MtRegularlyLocationRequest extends PagedRequest {

	private String phoneNumber;
	private Date fromDate;
	private Date toDate;

	public MtLocationFilter toFilter() {
		MtLocationFilter filter = new MtLocationFilter();
		filter.setPhoneNumber(phoneNumber);
		filter.setFromDate(fromDate);
		filter.setToDate(toDate);
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

	public void setFromDate(Object fromDate) {
		this.fromDate = DateUtils.setter(fromDate);
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Object toDate) {
		this.toDate = DateUtils.setter(toDate);
	}
}
