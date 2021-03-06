package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.RequestType;
import vn.metech.constant.SubscriberStatus;
import vn.metech.constant.VerifyService;
import vn.metech.dto.request.MtAdRequest;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;
import static vn.metech.constant.RequestStatus.PENDING;

@Entity
@Table(name = "AdRequest")
@Where(clause = "IsDeleted = 0")
public class AdRequest extends BaseEntity {

	@Enumerated(STRING)
	@Column(name = "VerifyService", length = 50)
	private VerifyService verifyService;

	@Enumerated(STRING)
	@Column(name = "RequestType", length = 50)
	private RequestType requestType;

	@Enumerated(STRING)
	@Column(name = "SubscriberStatus", length = 100)
	private SubscriberStatus subscriberStatus;

	@Column(name = "CustomerRequestId", length = 36)
	private String customerRequestId;

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Enumerated(STRING)
	@Column(name = "RequestStatus")
	private RequestStatus requestStatus;

	@Enumerated(STRING)
	@Column(name = "MbfStatus", length = 50)
	private MbfStatus mbfStatus;

	@Column(name = "CustomerCode", length = 12)
	private String customerCode;

	@Column(name = "RemoteAddr", length = 20)
	private String remoteAddr;

	@Temporal(TIMESTAMP)
	@Column(name = "DateToCheck")
	private Date dateToCheck;

	@Column(name = "IsDuplicate", columnDefinition = "bit default 1")
	private boolean duplicate;

	@Column(name = "DuplicateWith", length = 36)
	private String duplicateWith;

	@Column(name = "partner_id")
	private String partnerId;

	@Column(name = "sub_partner")
	private String subPartnerName;

	@Column(name = "FetchTimes", length = 1, columnDefinition = "int default 0")
	private Integer fetchTimes;

	@Temporal(TIMESTAMP)
	@Column(name = "LastFetch")
	private Date lastFetch;

	@Column(name = "GroupSync", columnDefinition = "bit default 0")
	private Boolean groupSync;

	@Column(name = "ResponseId", length = 36, insertable = false, updatable = false)
	private String responseId;

	@Column(name = "IsCharged", columnDefinition = "bit default 0")
	private Boolean charged;

	@JoinColumn(name = "ResponseId")
	@ManyToOne(fetch = LAZY)
	private AdResponse adResponse;

	public AdRequest() {
		this.fetchTimes = 0;
		this.duplicate = false;
		this.requestStatus = PENDING;
		this.charged = false;
	}

	public AdRequest(MtAdRequest mtRequest, String userId, String remoteAddr) {
		this();
		this.setPhoneNumber(mtRequest.getPhoneNumber());
		this.customerCode = mtRequest.getCustomerCode();
		this.customerRequestId = mtRequest.getRequestId();
		this.partnerId = mtRequest.getPartnerId();
		this.subPartnerName = mtRequest.getSubPartnerName();
		this.remoteAddr = remoteAddr;
		this.createdBy = userId;
		this.dateToCheck = mtRequest.getDateToCheck();
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
		this.groupSync = false;
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

	public MbfStatus getMbfStatus() {
		return mbfStatus;
	}

	public void setMbfStatus(MbfStatus mbfStatus) {
		this.mbfStatus = mbfStatus;
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

	public boolean getGroupSync() {
		return groupSync == null ? false : groupSync;
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

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public AdResponse getAdResponse() {
		return adResponse;
	}

	public void setAdResponse(AdResponse adResponse) {
		this.adResponse = adResponse;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getSubPartnerName() {
		return subPartnerName;
	}

	public void setSubPartnerName(String subPartnerName) {
		this.subPartnerName = subPartnerName;
	}
}
