package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.MfsStatus;

public class MfsPastIdStatus {

	@JsonProperty("resultId1")
	private MfsStatus idStatus;

	public MfsStatus getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Object idStatus) {
		MfsStatus mfsStatus = MfsStatus.UNDEFINED;
		if (idStatus instanceof String) {
			try {
				mfsStatus = MfsStatus.of(Integer.parseInt((String) idStatus));
			} catch (Exception ignored) {
				mfsStatus = MfsStatus.valueOf((String) idStatus);
			}
		} else if (idStatus instanceof MfsStatus) {
			mfsStatus = (MfsStatus) idStatus;
		}

		this.idStatus = mfsStatus;
	}
}
