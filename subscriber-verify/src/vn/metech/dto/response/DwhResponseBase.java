package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.DwhStatus;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DwhResponseBase implements Serializable {

	@JsonProperty("status")
	private Integer status;

	@JsonProperty("msg")
	private String message;

	@JsonProperty("requestId")
	private String requestId;

	public DwhStatus getDwhStatus() {
		return DwhStatus.of(status);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
