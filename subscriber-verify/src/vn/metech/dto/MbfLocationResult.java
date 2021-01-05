package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MbfLocationResult implements Serializable {

	@JsonProperty("data1")
	private List<MbfRegularlyLocation> regularlyLocations;

	@JsonProperty("data2")
	private List<MbfMostVisitLocation> mostVisitedLocations;

	public MbfLocationResult() {
		this.regularlyLocations = new ArrayList<>();
		this.mostVisitedLocations = new ArrayList<>();
	}

	public List<MbfRegularlyLocation> getRegularlyLocations() {
		return regularlyLocations;
	}

	public void setRegularlyLocations(List<MbfRegularlyLocation> regularlyLocations) {
		this.regularlyLocations = regularlyLocations;
	}

	public List<MbfMostVisitLocation> getMostVisitedLocations() {
		return mostVisitedLocations;
	}

	public void setMostVisitedLocations(List<MbfMostVisitLocation> mostVisitedLocations) {
		this.mostVisitedLocations = mostVisitedLocations;
	}
}
