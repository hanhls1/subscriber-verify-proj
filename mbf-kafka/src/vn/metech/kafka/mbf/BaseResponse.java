package vn.metech.kafka.mbf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class BaseResponse {

	@JsonProperty("requestId")
	private String partnerRequestId;

	@JsonProperty("responseId")
	private String partnerResponseId;

	@JsonProperty("desc")
	private String description;

	@JsonProperty("status")
	private MbfStatus status;

	@JsonProperty("data")
	private Object responseBody;

	public BaseResponse() {
		this.status = MbfStatus.UNDEFINED;
	}

	public String getPartnerRequestId() {
		return partnerRequestId;
	}

	public void setPartnerRequestId(String partnerRequestId) {
		this.partnerRequestId = partnerRequestId;
	}

	public String getPartnerResponseId() {
		return partnerResponseId;
	}

	public void setPartnerResponseId(String partnerResponseId) {
		this.partnerResponseId = partnerResponseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MbfStatus getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		if (status instanceof String) {
			this.status = MbfStatus.valueOf((String) status);
		} else if (status instanceof Integer) {
			this.status = MbfStatus.valueOf((int) status);
		} else {
			this.status = MbfStatus.UNDEFINED;
		}
	}

	public Object getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}
}
