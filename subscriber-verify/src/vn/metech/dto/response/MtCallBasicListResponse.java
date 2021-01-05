package vn.metech.dto.response;

import org.springframework.util.Assert;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.ResponseStatus;
import vn.metech.dto.CallBasicResult;
import vn.metech.dto.Result;
import vn.metech.entity.CallResponse;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MtCallBasicListResponse implements Serializable {

	private String responseId;
	private ResponseStatus responseStatus;
	private String phoneNumber;
	private Result status1;
	private Result status2;
	private String createdDate;
	private String refPhone1;
	private String refPhone2;
	private MbfStatus partnerStatus;

	protected void setProperties(CallResponse callResponse) {
		this.responseId = callResponse.getId();
		this.refPhone1 = callResponse.getRefPhone1();
		this.refPhone2 = callResponse.getRefPhone2();
		this.phoneNumber = callResponse.getPhoneNumber();
		this.responseStatus = callResponse.getResponseStatus();
		this.partnerStatus = callResponse.getMbfStatus();
		this.createdDate = DateUtils.formatDate(callResponse.getCreatedDate());
		if (!StringUtils.isEmpty(callResponse.getResponseData())) {
			CallBasicResult callResult =
							JsonUtils.toObject(callResponse.getResponseData(), CallBasicResult.class);
			if (callResult != null && callResult.getCallStatus() != null) {
				this.status1 = callResult.getCallStatus().getStatus1();
				this.status2 = callResult.getCallStatus().getStatus2();
			}
		}
	}

	public static MtCallBasicListResponse from(CallResponse callResponse) {
		Assert.notNull(callResponse, "callResponse is null");

		MtCallBasicListResponse mtCallBasicListResponse = new MtCallBasicListResponse();
		mtCallBasicListResponse.setProperties(callResponse);

		return mtCallBasicListResponse;
	}

	public static List<MtCallBasicListResponse> fromCollection(Collection<CallResponse> callResponses) {
		if (callResponses == null || callResponses.isEmpty()) {
			return Collections.emptyList();
		}

		return callResponses.stream().map(MtCallBasicListResponse::from).collect(Collectors.toList());
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

	public MbfStatus getPartnerStatus() {
		return partnerStatus;
	}

	public void setPartnerStatus(MbfStatus partnerStatus) {
		this.partnerStatus = partnerStatus;
	}
}
