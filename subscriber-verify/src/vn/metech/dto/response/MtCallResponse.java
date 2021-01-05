package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.MbfStatus;
import vn.metech.util.StringUtils;

import java.io.Serializable;

public class MtCallResponse implements Serializable {

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("responseId")
	private String responseId;

	@JsonProperty("status")
	private Integer status;

	@JsonProperty("desc")
	private String description;

	@JsonProperty("data")
	private MtCallVerifyResponse data;

	public MtCallResponse() {
		this.applyMbfStatus(MbfStatus.REQUESTED);
	}

	public MtCallResponse(String requestId, String responseId, String phoneNumber) {
		this();
		this.requestId = requestId;
		this.responseId = responseId;
		this.phoneNumber = phoneNumber;
	}

	public MtCallResponse(String requestId, String responseId, String phoneNumber, MtCallVerifyResponse data) {
		this();
		this.requestId = requestId;
		this.responseId = responseId;
		this.phoneNumber = phoneNumber;
		this.data = data;
	}

	public MtCallResponse(
					String requestId, String responseId, MbfStatus status,
					String phoneNumber, MtCallVerifyResponse data) {
		this();

		this.data = data;
		this.requestId = requestId;
		this.responseId = responseId;
		this.phoneNumber = phoneNumber;

		this.applyMbfStatus(status);
	}

	public void applyMbfStatus(MbfStatus status) {
		this.status = status.value();
		this.description = status.message();
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MtCallVerifyResponse getData() {
		return data;
	}

	public void setData(MtCallVerifyResponse data) {
		this.data = data;
	}
}
