package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.*;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "CallResponse")
@Where(clause = "IsDeleted = 0")
public class CallResponse extends BaseEntity {

	@Enumerated(STRING)
	@Column(name = "VerifyService", length = 50)
	private VerifyService verifyService;

	@Enumerated(EnumType.STRING)
	@Column(name = "RequestType", length = 50)
	private RequestType requestType;

	@Enumerated(STRING)
	@Column(name = "SubscriberStatus", length = 100)
	private SubscriberStatus subscriberStatus;

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Column(name = "RefPhone1")
	private String refPhone1;

	@Column(name = "RefPhone2")
	private String refPhone2;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseStatus", length = 50)
	private ResponseStatus responseStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseCode", length = 50)
	private MbfStatus mbfStatus;

	@Column(name = "ResponseData", length = 3060)
	private String responseData;

	@MapKey(name = "id")
	@OneToMany(fetch = LAZY, mappedBy = "callResponse")
	private Map<String, CallRequest> callRequests;

	public CallResponse() {
		this.callRequests = new HashMap<>();
		this.responseStatus = ResponseStatus.PENDING;
	}

	public CallResponse(CallRequest request, MbfStatus mbfStatus, String responseData) {
		this();
		this.verifyService = request.getVerifyService();
		this.mbfStatus = mbfStatus;
		this.responseData = responseData;
		this.createdBy = request.getCreatedBy();
		this.setRefPhone1(request.getRefPhone1());
		this.setRefPhone2(request.getRefPhone2());
		this.requestType = request.getRequestType();
		this.setPhoneNumber(request.getPhoneNumber());
		this.callRequests.put(request.getId(), request);
	}

	public CallResponse(CallRequest callRequest) {
		this(callRequest, null, null);
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
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

	public String getRefPhone1() {
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
		if (!StringUtils.isEmpty(this.refPhone1)) {
			this.refPhone1 = refPhone1.replaceAll("[^0-9]", "");
		}
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
		if (!StringUtils.isEmpty(this.refPhone2)) {
			this.refPhone2 = refPhone2.replaceAll("[^0-9]", "");
		}
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public MbfStatus getMbfStatus() {
		return mbfStatus;
	}

	public void setMbfStatus(MbfStatus mbfStatus) {
		this.mbfStatus = mbfStatus;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public Map<String, CallRequest> getCallRequests() {
		return callRequests;
	}

	public void setCallRequests(Map<String, CallRequest> callRequests) {
		this.callRequests = callRequests;
	}
}
