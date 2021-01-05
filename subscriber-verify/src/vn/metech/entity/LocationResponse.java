package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.*;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "LocationResponse")
@Where(clause = "IsDeleted = 0")
public class LocationResponse extends BaseEntity {

	@Enumerated(STRING)
	@Column(name = "SubscriberStatus", length = 100)
	private SubscriberStatus subscriberStatus;

	@Enumerated(STRING)
	@Column(name = "VerifyService", length = 50)
	private VerifyService verifyService;

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Column(name = "Province", columnDefinition = "nvarchar(500)")
	private String province;

	@Column(name = "DateToCheck")
	private Date dateToCheck;

	@Enumerated(EnumType.STRING)
	@Column(name = "RequestType", length = 50)
	private RequestType requestType;

	@Column(name = "HomeAddress", columnDefinition = "nvarchar(500)")
	private String homeAddress;

	@Column(name = "WorkAddress", columnDefinition = "nvarchar(500)")
	private String workAddress;

	@Column(name = "RefAddress", columnDefinition = "nvarchar(500)")
	private String refAddress;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseStatus", length = 50)
	private ResponseStatus responseStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseCode", length = 50)
	private MbfStatus mbfStatus;

	@Column(name = "ResponseData", length = 3060)
	private String responseData;

	@MapKey(name = "id")
	@OneToMany(mappedBy = "locationResponse", fetch = LAZY)
	private Map<String, LocationRequest> locationRequests;

	public LocationResponse() {
		this.locationRequests = new HashMap<>();
		this.responseStatus = ResponseStatus.PENDING;
	}

	public LocationResponse(LocationRequest request, MbfStatus mbfStatus, String responseData) {
		this();
		this.createdBy = request.getCreatedBy();
		this.mbfStatus = mbfStatus;
		this.responseData = responseData;
		this.verifyService = request.getVerifyService();
		this.locationRequests.put(request.getId(), request);
		this.setPhoneNumber(request.getPhoneNumber());
		this.workAddress = request.getWorkAddress();
		this.homeAddress = request.getHomeAddress();
		this.refAddress = request.getReferAddress();
		this.dateToCheck = request.getDateToCheck();
		this.province = request.getProvince();
	}

	public LocationResponse(LocationRequest request) {
		this(request, null, null);
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(Date dateToCheck) {
		this.dateToCheck = dateToCheck;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getRefAddress() {
		return refAddress;
	}

	public void setRefAddress(String refAddress) {
		this.refAddress = refAddress;
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

	public Map<String, LocationRequest> getLocationRequests() {
		return locationRequests;
	}

	public void setLocationRequests(Map<String, LocationRequest> locationRequests) {
		this.locationRequests = locationRequests;
	}
}
