package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.ResponseStatus;
import vn.metech.constant.SubscriberStatus;
import vn.metech.constant.VerifyService;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "AdResponse")
@Where(clause = "IsDeleted = 0")
public class AdResponse extends BaseEntity {

	@Enumerated(STRING)
	@Column(name = "VerifyService", length = 50)
	private VerifyService verifyService;

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Enumerated(STRING)
	@Column(name = "SubscriberStatus", length = 100)
	private SubscriberStatus subscriberStatus;

	@Temporal(TemporalType.DATE)
	@Column(name = "DateToCheck")
	private Date dateToCheck;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseStatus", length = 50)
	private ResponseStatus responseStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseCode", length = 50)
	private MbfStatus mbfStatus;

	@Column(name = "ResponseData", length = 3060)
	private String responseData;

	@MapKey(name = "id")
	@OneToMany(fetch = LAZY, mappedBy = "adResponse")
	private Map<String, AdRequest> adRequests;

	public AdResponse() {
		this.adRequests = new HashMap<>();
		this.responseStatus = ResponseStatus.PENDING;
	}

	public AdResponse(AdRequest request, MbfStatus mbfStatus, String responseData) {
		this();
		this.mbfStatus = mbfStatus;
		this.responseData = responseData;
		this.verifyService = request.getVerifyService();
		this.createdBy = request.getCreatedBy();
		this.dateToCheck = request.getDateToCheck();
		this.setPhoneNumber(request.getPhoneNumber());
		this.adRequests.put(request.getId(), request);
	}

	public AdResponse(AdRequest request) {
		this(request, null, null);
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

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
		this.subscriberStatus = subscriberStatus;

	}

	public Date getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(Date dateToCheck) {
		this.dateToCheck = dateToCheck;
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

	public Map<String, AdRequest> getAdRequests() {
		return adRequests;
	}

	public void setAdRequests(Map<String, AdRequest> adRequests) {
		this.adRequests = adRequests;
	}
}
