package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsImeiStatus {

	@JsonProperty("report1")
	private MfsCurrentImeiStatus currentImeiStatus;

	@JsonProperty("report2")
	private MfsLatestImeiStatus latestImeiStatus;

	public MfsCurrentImeiStatus getCurrentImeiStatus() {
		return currentImeiStatus;
	}

	public void setCurrentImeiStatus(MfsCurrentImeiStatus currentImeiStatus) {
		this.currentImeiStatus = currentImeiStatus;
	}

	public MfsLatestImeiStatus getLatestImeiStatus() {
		return latestImeiStatus;
	}

	public void setLatestImeiStatus(MfsLatestImeiStatus latestImeiStatus) {
		this.latestImeiStatus = latestImeiStatus;
	}
}
