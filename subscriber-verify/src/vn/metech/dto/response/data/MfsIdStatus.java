package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsIdStatus {

	@JsonProperty("report1")
	private MfsCurrentIdStatus currentIdStatus;

	@JsonProperty("report2")
	private MfsPastIdStatus pastIdStatus;

	public MfsCurrentIdStatus getCurrentIdStatus() {
		return currentIdStatus;
	}

	public void setCurrentIdStatus(MfsCurrentIdStatus currentIdStatus) {
		this.currentIdStatus = currentIdStatus;
	}

	public MfsPastIdStatus getPastIdStatus() {
		return pastIdStatus;
	}

	public void setPastIdStatus(MfsPastIdStatus pastIdStatus) {
		this.pastIdStatus = pastIdStatus;
	}
}
