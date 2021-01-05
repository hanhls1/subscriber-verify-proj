package vn.metech.entity;

import vn.metech.constant.*;
import vn.metech.dto.request.MtCallRequest;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;
import static vn.metech.constant.RequestStatus.PENDING;

@Entity
@Table(name = "CallRequest")
//@Where(clause = "IsDeleted = 0")
public class CallRequest extends BaseEntity {

	@Enumerated(STRING)
	@Column(name = "VerifyService", length = 50)
	private VerifyService verifyService;

	@Enumerated(STRING)
	@Column(name = "MbfStatus", length = 50)
	private MbfStatus mbfStatus;

	@Enumerated(STRING)
	@Column(name = "SubscriberStatus", length = 100)
	private SubscriberStatus subscriberStatus;

	@Column(name = "IsBasic", columnDefinition = "bit")
	private Boolean basic;

	@Enumerated(STRING)
	@Column(name = "RequestType")
	private RequestType requestType;

	@Column(name = "CustomerRequestId", length = 36)
	private String customerRequestId;

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Enumerated(STRING)
	@Column(name = "RequestStatus")
	private RequestStatus requestStatus;

	@Column(name = "CustomerCode", length = 12)
	private String customerCode;

	@Column(name = "RemoteAddr", length = 20)
	private String remoteAddr;

	@Column(name = "RefPhone1", length = 20)
	private String refPhone1;

	@Column(name = "RefPhone2", length = 20)
	private String refPhone2;

	@Column(name = "IsDuplicate", columnDefinition = "bit default 1")
	private boolean duplicate;

	@Column(name = "DuplicateWith", length = 36)
	private String duplicateWith;

	@Column(name = "FetchTimes", length = 1, columnDefinition = "int default 0")
	private Integer fetchTimes;

	@Temporal(TIMESTAMP)
	@Column(name = "LastFetch")
	private Date lastFetch;

	@Column(name = "ResponseId", length = 36, insertable = false, updatable = false)
	private String responseId;

	@Column(name = "GroupSync", columnDefinition = "bit default 0")
	private Boolean groupSync;

	@Column(name = "IsCharged", columnDefinition = "bit default 0")
	private Boolean charged;

	@JoinColumn(name = "ResponseId")
	@ManyToOne(fetch = LAZY)
	private CallResponse callResponse;

	public CallRequest() {
		this.basic = false;
		this.fetchTimes = 0;
		this.duplicate = false;
		this.groupSync = false;
		this.requestStatus = PENDING;
		this.charged = false;
	}

	public CallRequest(MtCallRequest mtRequest, String userId, String remoteAddr) {
		this();
		this.basic = mtRequest.getBasic();
		this.setPhoneNumber(mtRequest.getPhoneNumber());
		this.customerCode = mtRequest.getCustomerCode();
		this.customerRequestId = mtRequest.getRequestId();
		this.remoteAddr = remoteAddr;
		this.createdBy = userId;
		this.refPhone1 = mtRequest.getRefPhone1();
		this.refPhone2 = mtRequest.getRefPhone2();
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}

	public MbfStatus getMbfStatus() {
		return mbfStatus;
	}

	public void setMbfStatus(MbfStatus mbfStatus) {
		this.mbfStatus = mbfStatus;
	}

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
		this.groupSync = false;
	}

	public boolean getBasic() {
		return basic == null ? false : basic;
	}

	public void setBasic(boolean basic) {
		this.basic = basic;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getCustomerRequestId() {
		return customerRequestId;
	}

	public void setCustomerRequestId(String customerRequestId) {
		this.customerRequestId = customerRequestId;
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

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.fetchTimes = 0;
		this.lastFetch = null;
		this.requestStatus = requestStatus;
		this.groupSync = false;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
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

	public boolean isDuplicate() {
		return duplicate;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	public String getDuplicateWith() {
		return duplicateWith;
	}

	public void setDuplicateWith(String duplicateWith) {
		this.duplicateWith = duplicateWith;
	}

	public Integer getFetchTimes() {
		return fetchTimes;
	}

	public void setFetchTimes(Integer fetchTimes) {
		this.fetchTimes = fetchTimes;
	}

	public Date getLastFetch() {
		return lastFetch;
	}

	public void setLastFetch(Date lastFetch) {
		this.lastFetch = lastFetch;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Boolean getGroupSync() {
		return groupSync;
	}

	public void setGroupSync(boolean groupSync) {
		this.groupSync = groupSync;
		this.setFetchTimes(0);
		this.setLastFetch(null);
	}

	public Boolean getCharged() {
		return charged;
	}

	public void setCharged(Boolean charged) {
		this.charged = charged;
		this.setFetchTimes(0);
		this.setLastFetch(null);
	}

	public CallResponse getCallResponse() {
		return callResponse;
	}

	public void setCallResponse(CallResponse callResponse) {
		this.callResponse = callResponse;
	}

}
