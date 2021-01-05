package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsQRequestId {

	@JsonProperty("qRequestId")
	private String qRequestId;

	public String getqRequestId() {
		return qRequestId;
	}

	public void setqRequestId(String qRequestId) {
		this.qRequestId = qRequestId;
	}
}
