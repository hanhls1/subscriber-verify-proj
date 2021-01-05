package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsCallBasic {

	@JsonProperty("report1")
	private MfsCallStatus statusReport;

	public MfsCallStatus getStatusReport() {
		return statusReport;
	}

	public void setStatusReport(MfsCallStatus statusReport) {
		this.statusReport = statusReport;
	}
}
