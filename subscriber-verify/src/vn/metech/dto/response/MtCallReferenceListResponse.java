package vn.metech.dto.response;

import org.springframework.util.Assert;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.ResponseStatus;
import vn.metech.constant.SubscriberStatus;
import vn.metech.constant.VerifyService;
import vn.metech.dto.CallBasicResult;
import vn.metech.dto.MbfCallReferenceResult;
import vn.metech.dto.Result;
import vn.metech.entity.CallResponse;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MtCallReferenceListResponse {

	private String responseId;
	private ResponseStatus responseStatus;
	private String phoneNumber;
	private Result status1;
	private Result status2;
	private String createdDate;
	private String refPhone1;
	private String refPhone2;
	private String freq1;
	private String freq2;
	private String dur1;
	private String dur2;
	private MbfStatus partnerStatus;
	private SubscriberStatus subscriberStatus;

	protected void setProperties(CallResponse callResponse) {
		this.responseId = callResponse.getId();
		this.refPhone1 = callResponse.getRefPhone1();
		this.refPhone2 = callResponse.getRefPhone2();
		this.phoneNumber = callResponse.getPhoneNumber();
		this.responseStatus = callResponse.getResponseStatus();
		this.partnerStatus = callResponse.getMbfStatus();
		this.createdDate = DateUtils.formatDate(callResponse.getCreatedDate());
		this.subscriberStatus = callResponse.getSubscriberStatus();
		if (!StringUtils.isEmpty(callResponse.getResponseData())) {
			if (callResponse.getVerifyService() == VerifyService.FC04_02) {
				CallBasicResult callResult = JsonUtils.toObject(callResponse.getResponseData(), CallBasicResult.class);
				if (callResult != null && callResult.getCallStatus() != null) {
					this.status1 = callResult.getCallStatus().getStatus1();
					this.status2 = callResult.getCallStatus().getStatus2();
				}
			}
			if (callResponse.getVerifyService() == VerifyService.FC04_01) {
				MbfCallReferenceResult callResult =
								JsonUtils.toObject(callResponse.getResponseData(), MbfCallReferenceResult.class);
				if (callResult != null && callResult.getCallFrequency() != null) {
					this.dur1 = callResult.getCallFrequency().getDur1();
					this.dur1 = callResult.getCallFrequency().getDur2();
					this.freq1 = callResult.getCallFrequency().getFreq1();
					this.freq2 = callResult.getCallFrequency().getFreq2();
				}
			}
		}
	}

	public static MtCallReferenceListResponse from(CallResponse callResponse) {
		Assert.notNull(callResponse, "callResponse is null");

		MtCallReferenceListResponse callReferenceListResponse = new MtCallReferenceListResponse();
		callReferenceListResponse.setProperties(callResponse);

		return callReferenceListResponse;
	}

	public static List<MtCallReferenceListResponse> fromCollection(Collection<CallResponse> callResponses) {
		if (callResponses == null || callResponses.isEmpty()) {
			return Collections.emptyList();
		}

		return callResponses.stream().map(MtCallReferenceListResponse::from).collect(Collectors.toList());
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
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

	public Result getStatus1() {
		return status1;
	}

	public void setStatus1(Result status1) {
		this.status1 = status1;
	}

	public Result getStatus2() {
		return status2;
	}

	public void setStatus2(Result status2) {
		this.status2 = status2;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

	public String getFreq1() {
		return freq1;
	}

	public void setFreq1(String freq1) {
		this.freq1 = freq1;
	}

	public String getFreq2() {
		return freq2;
	}

	public void setFreq2(String freq2) {
		this.freq2 = freq2;
	}

	public String getDur1() {
		return dur1;
	}

	public void setDur1(String dur1) {
		this.dur1 = dur1;
	}

	public String getDur2() {
		return dur2;
	}

	public void setDur2(String dur2) {
		this.dur2 = dur2;
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
