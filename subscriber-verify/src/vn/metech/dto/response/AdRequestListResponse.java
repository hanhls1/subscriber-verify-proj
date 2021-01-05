package vn.metech.dto.response;

import org.springframework.util.Assert;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.SubscriberStatus;
import vn.metech.entity.AdRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AdRequestListResponse implements Serializable {

	private String requestId;
	private String requestType;
	private String createdDate;
	private String phoneNumber;
	private RequestStatus requestStatus;
	private MbfStatus partnerStatus;
	private SubscriberStatus subscriberStatus;

	protected void setProperties(AdRequest adRequest) {
		this.requestId = adRequest.getId();
		if (adRequest.getRequestType() != null) {
			switch (adRequest.getRequestType()) {
				case PAST_CHECK:
					this.requestType = "IMEI cũ";
					break;
				case CURRENT_CHECK:
					this.requestType = "IMEI hiện tại";
					break;
			}
		}
		this.createdDate = DateUtils.formatDate(adRequest.getCreatedDate());
		this.phoneNumber = adRequest.getPhoneNumber();
		this.requestStatus = adRequest.getRequestStatus();
		this.partnerStatus = adRequest.getMbfStatus();
		this.subscriberStatus = adRequest.getSubscriberStatus();
	}

	public static AdRequestListResponse from(AdRequest adRequest) {
		Assert.notNull(adRequest, "adRequest is null");

		AdRequestListResponse adRequestResponse = new AdRequestListResponse();
		adRequestResponse.setProperties(adRequest);

		return adRequestResponse;
	}

	public static List<AdRequestListResponse> fromCollection(Collection<AdRequest> adRequests) {
		if (adRequests == null || adRequests.isEmpty()) {
			return Collections.emptyList();
		}

		return adRequests.stream().map(AdRequestListResponse::from).collect(Collectors.toList());
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
		this.requestStatus = requestStatus;
	}

	public MbfStatus getPartnerStatus() {
		return partnerStatus;
	}

	public void setPartnerStatus(MbfStatus partnerStatus) {
		this.partnerStatus = partnerStatus;
	}

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus != null ? subscriberStatus : SubscriberStatus.NO_STATUS;
	}

	public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}
}
