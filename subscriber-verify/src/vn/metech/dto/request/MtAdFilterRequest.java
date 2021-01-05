package vn.metech.dto.request;

import vn.metech.constant.RequestStatus;
import vn.metech.constant.VerifyService;
import vn.metech.dto.MtAdFilter;
import vn.metech.shared.PagedRequest;
import vn.metech.util.StringUtils;

import java.util.Date;

public class MtAdFilterRequest extends PagedRequest {

	private RequestStatus requestStatus;
	private String phoneNumber;
	private Date fromDate;
	private Date toDate;
	private VerifyService serviceType;

	public MtAdFilter toFilter() {
		MtAdFilter filter = new MtAdFilter();
		filter.setRequestStatus(requestStatus);
		filter.setPhoneNumber(phoneNumber);
		filter.setServiceType(serviceType);
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

	public void setServiceType(Object serviceType) {
		if (serviceType instanceof String) {
			try {
				this.serviceType = VerifyService.of((String) serviceType);
			} catch (Exception ignored) {
			}
			if (StringUtils.isEmpty(this.serviceType)) {
				try {
					this.serviceType = VerifyService.valueOf((String) serviceType);
				} catch (Exception ignored) {
				}
			}
		} else if (serviceType instanceof VerifyService) {
			this.serviceType = (VerifyService) serviceType;
		}
	}
}
