package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MfsLocationAdvance {

	@JsonProperty("data2")
	private List<MfsMostVisitLocation> mostVisitLocations;

	@JsonProperty("data1")
	private List<MfsLocationFrequency> locationFrequencies;

	public MfsLocationAdvance() {
		this.mostVisitLocations = new ArrayList<>();
		this.locationFrequencies = new ArrayList<>();
	}

	public List<MfsMostVisitLocation> getMostVisitLocations() {
		return mostVisitLocations;
	}

	public void setMostVisitLocations(List<MfsMostVisitLocation> mostVisitLocations) {
		this.mostVisitLocations = mostVisitLocations;
	}

	public List<MfsLocationFrequency> getLocationFrequencies() {
		return locationFrequencies;
	}

	public void setLocationFrequencies(List<MfsLocationFrequency> locationFrequencies) {
		this.locationFrequencies = locationFrequencies;
	}
}
