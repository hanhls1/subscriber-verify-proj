package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class BaseRequest {

	@JsonProperty("requestId")
	private String partnerRequestId;

	public BaseRequest() {
	}

	public String getPartnerRequestId() {
		return partnerRequestId;
	}

	public void setPartnerRequestId(String partnerRequestId) {
		this.partnerRequestId = partnerRequestId;
	}
}
