package vn.metech.dto.response.data;

import java.util.ArrayList;
import java.util.List;

public class Combo01Advance {

	private List<MostVisitLocation> mostVisitLocations;
	private List<RegularlyLocation> regularlyLocations;
	private List<CallBase> referenceReports;
	private List<CallBase> frequencyReports;

	public Combo01Advance() {
		this.mostVisitLocations = new ArrayList<>();
		this.regularlyLocations = new ArrayList<>();
		this.referenceReports = new ArrayList<>();
		this.frequencyReports = new ArrayList<>();
	}

	public Combo01Advance(LocationAdvance locationAdvance, CallReference callReference) {
		this.mostVisitLocations = locationAdvance.getMostVisitLocations();
		this.regularlyLocations = locationAdvance.getRegularlyLocations();
		this.referenceReports = callReference.getReferenceReports();
		this.frequencyReports = callReference.getFrequencyReports();
	}

	public List<MostVisitLocation> getMostVisitLocations() {
		return mostVisitLocations;
	}

	public void setMostVisitLocations(List<MostVisitLocation> mostVisitLocations) {
		this.mostVisitLocations = mostVisitLocations;
	}

	public List<RegularlyLocation> getRegularlyLocations() {
		return regularlyLocations;
	}

	public void setRegularlyLocations(List<RegularlyLocation> regularlyLocations) {
		this.regularlyLocations = regularlyLocations;
	}

	public List<CallBase> getReferenceReports() {
		return referenceReports;
	}

	public void setReferenceReports(List<CallBase> referenceReports) {
		this.referenceReports = referenceReports;
	}

	public List<CallBase> getFrequencyReports() {
		return frequencyReports;
	}

	public void setFrequencyReports(List<CallBase> frequencyReports) {
		this.frequencyReports = frequencyReports;
	}
}
