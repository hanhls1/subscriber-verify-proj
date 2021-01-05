package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.SubscriberStatus;
import vn.metech.constant.VerifyService;
import vn.metech.dto.request.MtIdRequest;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;
import static vn.metech.constant.RequestStatus.PENDING;

@Entity
@Table(name = "IdRequest")
@Where(clause = "IsDeleted = 0")
public class IdRequest extends BaseEntity {

	@Enumerated(STRING)
	@Column(name = "SubscriberStatus", length = 100)
	private SubscriberStatus subscriberStatus;

	@Enumerated(STRING)
	@Column(name = "MbfStatus", length = 50)
	private MbfStatus mbfStatus;

	@Enumerated(STRING)
	@Column(name = "VerifyService", length = 50)
	private VerifyService verifyService;

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

	@Column(name = "IdNumber", length = 20)
	private String idNumber;

	@Temporal(DATE)
	@Column(name = "DateToCheck")
	private Date dateToCheck;

	@Column(name = "IsDuplicate", columnDefinition = "bit default 1")
	private boolean duplicate;

	@Column(name = "DuplicateWith", length = 36)
	private String duplicateWith;

	@Column(name = "FetchTimes", length = 1, columnDefinition = "int default 0")
	private Integer fetchTimes;

	@Temporal(TIMESTAMP)
	@Column(name = "LastFetch")
	private Date lastFetch;

	@Column(name = "IsCharged", columnDefinition = "bit default 0")
	private Boolean charged;

	@Column(name = "ResponseId", length = 36, updatable = false, insertable = false)
	private String responseId;

	@JoinColumn(name = "ResponseId")
	@ManyToOne(fetch = LAZY)
	private IdResponse idResponse;

	public IdRequest() {
		this.fetchTimes = 0;
		this.duplicate = false;
		this.requestStatus = PENDING;
		this.charged = false;
	}

	public IdRequest(MtIdRequest mtRequest, String userId, String remoteAddr) {
		this();
		this.setPhoneNumber(mtRequest.getPhoneNumber());
		this.customerCode = mtRequest.getCustomerCode();
		this.customerRequestId = mtRequest.getRequestId();
		this.remoteAddr = remoteAddr;
		this.createdBy = userId;
		this.idNumber = mtRequest.getIdNumber();
		this.dateToCheck = mtRequest.getDateToCheck();
	}

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

	public MbfStatus getMbfStatus() {
		return mbfStatus;
	}

	public void setMbfStatus(MbfStatus mbfStatus) {
		this.mbfStatus = mbfStatus;
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Date getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(Date dateToCheck) {
		this.dateToCheck = dateToCheck;
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

	public Boolean getCharged() {
		return charged;
	}

	public void setCharged(Boolean charged) {
		this.charged = charged;
		this.fetchTimes = 0;
		this.lastFetch = null;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public IdResponse getIdResponse() {
		return idResponse;
	}

	public void setIdResponse(IdResponse idResponse) {
		this.idResponse = idResponse;
	}
}
