package vn.metech.entity;

import vn.metech.constant.AppService;
import vn.metech.constant.RequestStatus;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "TpcConfirmInfo")
public class TpcConfirmInfo extends BaseEntity {

	@Column(name = "AppService", length = 100)
	@Enumerated(EnumType.STRING)
	private AppService appService;

	@Enumerated(EnumType.STRING)
	@Column(name = "[RequestStatus]", length = 50)
	private RequestStatus requestStatus;

	@Column(name = "SubscriberStatus", length = 100)
	private String subscriberStatus;

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Column(name = "RequestId", length = 36)
	private String requestId;

	@Column(name = "Result1", length = 50)
	private String result1;

	@Column(name = "Result2", length = 50)
	private String result2;

	@Column(name = "ConfirmId", length = 36)
	private String confirmId;

	private Boolean timeout;

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getResult1() {
		return result1;
	}

	public void setResult1(String result1) {
		this.result1 = result1;
	}

	public String getResult2() {
		return result2;
	}

	public void setResult2(String result2) {
		this.result2 = result2;
	}

	public String getConfirmId() {
		return confirmId;
	}

	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}

	public Boolean getTimeout() {
		return timeout;
	}

	public void setTimeout(Boolean timeout) {
		this.timeout = timeout;
	}
}
