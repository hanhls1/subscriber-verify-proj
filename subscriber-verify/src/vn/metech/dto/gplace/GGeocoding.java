package vn.metech.dto.gplace;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.GMapStatus;

import java.util.ArrayList;
import java.util.List;

public class GGeocoding {

	private GMapStatus status;

	@JsonProperty("plus_code")
	private GGeocodingPlusCode plusCode;

	@JsonProperty("results")
	private List<GGeocodingResult> results;

	public GGeocoding() {
		this.status = GMapStatus.UNKNOWN_ERROR;
		this.results = new ArrayList<>();
	}

	public GMapStatus getStatus() {
		return status;
	}

	public void setStatus(GMapStatus status) {
		this.status = status;
	}

	public GGeocodingPlusCode getPlusCode() {
		return plusCode;
	}

	public void setPlusCode(GGeocodingPlusCode plusCode) {
		this.plusCode = plusCode;
	}

	public List<GGeocodingResult> getResults() {
		return results;
	}

	public void setResults(List<GGeocodingResult> results) {
		this.results = results;
	}
}
