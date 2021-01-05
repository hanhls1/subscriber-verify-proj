package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.*;
import vn.metech.dto.request.MtLocationRequest;
import vn.metech.shared.BaseEntity;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "LocationRequest")
@Where(clause = "IsDeleted = 0")
public class LocationRequest extends BaseEntity {

	@Enumerated(STRING)
	@Column(name = "SubscriberStatus", length = 100)
	private SubscriberStatus subscriberStatus;

	@Enumerated(STRING)
	@Column(name = "VerifyService", length = 50)
	private VerifyService verifyService;

	@Enumerated(STRING)
	@Column(name = "AppService", length = 50)
	private AppService appService;

	@Enumerated(STRING)
	@Column(name = "MbfStatus", length = 50)
	private MbfStatus mbfStatus;

	@Enumerated(STRING)
	@Column(name = "RequestType", length = 50)
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

	@Column(name = "TotalDate", length = 5)
	private Integer totalDate;

	@Column(name = "Province", columnDefinition = "nvarchar(500)")
	private String province;

	@Column(name = "HomeStr", columnDefinition = "nvarchar(255)")
	private String home;

	@Column(name = "WorkStr", columnDefinition = "nvarchar(255)")
	private String work;

	@Column(name = "RefStr", columnDefinition = "nvarchar(255)")
	private String ref;

	@Column(name = "WorkAddress", columnDefinition = "nvarchar(500)")
	private String workAddress;

	@Column(name = "HomeAddress", columnDefinition = "nvarchar(500)")
	private String homeAddress;

	@Column(name = "ReferAddress", columnDefinition = "nvarchar(500)")
	private String referAddress;

	@Temporal(TemporalType.DATE)
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

	@Column(name = "ResponseId", length = 36, insertable = false, updatable = false)
	private String responseId;

	@Column(name = "IsCharged", columnDefinition = "bit default 0")
	private Boolean charged;

	@JoinColumn(name = "ResponseId")
	@ManyToOne(fetch = LAZY)
	private LocationResponse locationResponse;

	public LocationRequest() {
		this.duplicate = false;
		this.requestStatus = RequestStatus.PENDING;
		this.dateToCheck = new Date();
		this.fetchTimes = 0;
		this.charged = false;
	}

	public LocationRequest(MtLocationRequest mtRequest, String userId, String remoteAddr) {
		this();
		this.setPhoneNumber(mtRequest.getPhoneNumber());
		this.customerCode = mtRequest.getCustomerCode();
		this.totalDate = mtRequest.getTotalDate();
		this.customerRequestId = mtRequest.getRequestId();
		this.dateToCheck = mtRequest.getDateToCheck();
		this.remoteAddr = remoteAddr;
		this.createdBy = userId;
		this.homeAddress = JsonUtils.toJson(mtRequest.getHomeAddress());
		this.workAddress = JsonUtils.toJson(mtRequest.getWorkAddress());
		this.referAddress = JsonUtils.toJson(mtRequest.getReferAddress());
		this.home = mtRequest.getHomeAddress().getAddress();
		this.work = mtRequest.getWorkAddress().getAddress();
		this.ref = mtRequest.getReferAddress().getAddress();
	}

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public MbfStatus getMbfStatus() {
		return mbfStatus;
	}

	public void setMbfStatus(MbfStatus mbfStatus) {
		this.mbfStatus = mbfStatus;
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

	public Integer getTotalDate() {
		return totalDate;
	}

	public void setTotalDate(Integer totalDate) {
		this.totalDate = totalDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getReferAddress() {
		return referAddress;
	}

	public void setReferAddress(String referAddress) {
		this.referAddress = referAddress;
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

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Boolean getCharged() {
		return charged;
	}

	public void setCharged(Boolean charged) {
		this.charged = charged;
		this.fetchTimes = 0;
		this.lastFetch = null;
	}

	public LocationResponse getLocationResponse() {
		return locationResponse;
	}

	public void setLocationResponse(LocationResponse locationResponse) {
		this.locationResponse = locationResponse;
	}
}
