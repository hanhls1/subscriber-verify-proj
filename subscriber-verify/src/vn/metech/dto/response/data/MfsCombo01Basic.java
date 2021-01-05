package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.dto.response.MfsGenericResponse;

public class MfsCombo01Basic {

	@JsonProperty("callReference")
	private MfsGenericResponse<MfsCallBasic> callBasic;

	@JsonProperty("cac")
	private MfsGenericResponse<MfsLocationAdvance> locationAdvance;

	public MfsGenericResponse<MfsCallBasic> getCallBasic() {
		return callBasic;
	}

	public void setCallBasic(MfsGenericResponse<MfsCallBasic> callBasic) {
		this.callBasic = callBasic;
	}

	public MfsGenericResponse<MfsLocationAdvance> getLocationAdvance() {
		return locationAdvance;
	}

	public void setLocationAdvance(MfsGenericResponse<MfsLocationAdvance> locationAdvance) {
		this.locationAdvance = locationAdvance;
	}
}
