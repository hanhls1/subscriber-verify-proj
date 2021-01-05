package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.dto.response.MfsGenericResponse;

public class MfsCombo02Basic extends MfsCombo01Basic {

	@JsonProperty("adReference")
	private MfsGenericResponse<MfsImeiStatus> imeiStatus;

	public MfsGenericResponse<MfsImeiStatus> getImeiStatus() {
		return imeiStatus;
	}

	public void setImeiStatus(MfsGenericResponse<MfsImeiStatus> imeiStatus) {
		this.imeiStatus = imeiStatus;
	}
}
