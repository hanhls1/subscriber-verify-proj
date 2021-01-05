package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfsGetSuccessRequest extends MfsRequestBase {

	@JsonProperty("qRequestId")
	private String qRequestId;

	public MfsGetSuccessRequest(String qRequestId, String customerCode, String account, String hashKey) {
		super(account, customerCode, "", hashKey);
		this.qRequestId = qRequestId;
	}

	public String getqRequestId() {
		return qRequestId;
	}

	public void setqRequestId(String qRequestId) {
		this.qRequestId = qRequestId;
	}
}
