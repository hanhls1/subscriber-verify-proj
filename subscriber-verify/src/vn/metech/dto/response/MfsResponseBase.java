package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.MfsStatus;

public abstract class MfsResponseBase {

	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("responseId")
	private String responseId;

	@JsonProperty("status")
	private Integer status;

	@JsonProperty("desc")
	private String description;

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

	public MfsStatus getMfsStatus() {
		MfsStatus status = MfsStatus.of(this.status);

		return status != null ? status : MfsStatus.UNDEFINED;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
