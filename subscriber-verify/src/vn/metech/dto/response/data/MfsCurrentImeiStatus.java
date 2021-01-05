package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.MfsStatus;

public class MfsCurrentImeiStatus {

	@JsonProperty("resultImei1")
	private MfsStatus imeiStatus;

	@JsonProperty("dateCheckImei")
	private String checkDate;

	public MfsStatus getImeiStatus() {
		return imeiStatus;
	}

	public void setImeiStatus(Object imeiStatus) {
		MfsStatus mfsStatus = MfsStatus.UNDEFINED;
		if (imeiStatus instanceof String) {
			try {
				mfsStatus = MfsStatus.of(Integer.parseInt((String) imeiStatus));
			} catch (Exception ignored) {
				mfsStatus = MfsStatus.valueOf((String) imeiStatus);
			}
		} else if (imeiStatus instanceof MfsStatus) {
			mfsStatus = (MfsStatus) imeiStatus;
		}

		this.imeiStatus = mfsStatus;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}
