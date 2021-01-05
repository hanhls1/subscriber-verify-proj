package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.dto.response.MfsGenericResponse;

public class MfsCombo01Advance {

	@JsonProperty("callReference")
	private MfsGenericResponse<MfsCallAdvance> callAdvance;

	@JsonProperty("cac")
	private MfsGenericResponse<MfsLocationAdvance> locationAdvance;

	public MfsGenericResponse<MfsCallAdvance> getCallAdvance() {
		return callAdvance;
	}

	public void setCallAdvance(MfsGenericResponse<MfsCallAdvance> callAdvance) {
		this.callAdvance = callAdvance;
	}

	public MfsGenericResponse<MfsLocationAdvance> getLocationAdvance() {
		return locationAdvance;
	}

	public void setLocationAdvance(MfsGenericResponse<MfsLocationAdvance> locationAdvance) {
		this.locationAdvance = locationAdvance;
	}

}
