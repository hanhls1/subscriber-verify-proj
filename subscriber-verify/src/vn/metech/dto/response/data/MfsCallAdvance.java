package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsCallAdvance {

	@JsonProperty("report1")
	private MfsCallStatus statusReport;

	@JsonProperty("report2")
	private MfsCallFrequency freqReport;

	public MfsCallStatus getStatusReport() {
		return statusReport;
	}

	public void setStatusReport(MfsCallStatus statusReport) {
		this.statusReport = statusReport;
	}

	public MfsCallFrequency getFreqReport() {
		return freqReport;
	}

	public void setFreqReport(MfsCallFrequency freqReport) {
		this.freqReport = freqReport;
	}
}
