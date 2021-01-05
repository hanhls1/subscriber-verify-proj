package vn.metech.dto.response;

import org.springframework.util.Assert;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.SubscriberStatus;
import vn.metech.entity.CallRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MtCallRequestListResponse implements Serializable {

	private String requestId;
	private String phoneNumber;
	private String refPhone1;
	private String refPhone2;
	private String createdDate;
	private RequestStatus requestStatus;
	private MbfStatus partnerStatus;
	private SubscriberStatus subscriberStatus;

	public static MtCallRequestListResponse from(CallRequest callRequest) {
		Assert.notNull(callRequest, "[callRequest] is null");

		MtCallRequestListResponse requestResponse = new MtCallRequestListResponse();
		requestResponse.requestId = callRequest.getId();
		requestResponse.phoneNumber = callRequest.getPhoneNumber();
		requestResponse.refPhone1 = callRequest.getRefPhone1();
		requestResponse.refPhone2 = callRequest.getRefPhone2();
		requestResponse.createdDate = DateUtils.formatDate(callRequest.getCreatedDate());
		requestResponse.requestStatus = callRequest.getRequestStatus();
		requestResponse.partnerStatus = callRequest.getMbfStatus();
		requestResponse.subscriberStatus = callRequest.getSubscriberStatus();

		return requestResponse;
	}

	public static List<MtCallRequestListResponse> fromList(List<CallRequest> callRequests) {
		List<MtCallRequestListResponse> requestResponses = new ArrayList<>();
		for (CallRequest callRequest : callRequests) {
			requestResponses.add(from(callRequest));
		}

		return requestResponses;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
