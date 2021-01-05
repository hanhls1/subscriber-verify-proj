package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.dto.response.MfsGenericResponse;

public class MfsCombo03Basic extends MfsCombo02Basic {

	@JsonProperty("idReference")
	private MfsGenericResponse<MfsIdStatus> idStatus;

	public MfsGenericResponse<MfsIdStatus> getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(MfsGenericResponse<MfsIdStatus> idStatus) {
		this.idStatus = idStatus;
	}
}
