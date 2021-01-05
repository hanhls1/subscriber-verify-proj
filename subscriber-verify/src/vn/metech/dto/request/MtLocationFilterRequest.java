package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.constant.VerifyService;
import vn.metech.shared.PagedRequest;
import vn.metech.util.StringUtils;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MtLocationFilterRequest extends PagedRequest {

	private String phoneNumber;
	private Date fromDate;
	private Date toDate;
	private VerifyService serviceType;

	public MtLocationFilterRequest() {
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
