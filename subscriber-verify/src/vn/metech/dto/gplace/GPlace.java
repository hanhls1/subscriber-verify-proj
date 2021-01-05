package vn.metech.dto.gplace;


import vn.metech.common.GMapStatus;

import java.util.ArrayList;
import java.util.List;

public class GPlace {

	private GMapStatus status;
	private List<GPlaceCandidate> candidates;

	public GPlace() {
		this.status = GMapStatus.UNKNOWN_ERROR;
		this.candidates = new ArrayList<>();
	}

	public GPlaceLocation location() {
		return candidates.size() == 0 ? null : candidates.get(0).getGeometry().getLocation();
	}

	public GMapStatus getStatus() {
		return status;
	}

	public void setStatus(GMapStatus status) {
		this.status = status;
	}

	public List<GPlaceCandidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<GPlaceCandidate> candidates) {
		this.candidates = candidates;
	}
}
