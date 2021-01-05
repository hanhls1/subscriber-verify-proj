package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Result;

public class MfsCurrentIdStatus {

	@JsonProperty("resultId2")
	private Result idStatus;

	@JsonProperty("dateCheckId")
	private String checkDate;

	public Result getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Object idStatus) {
		Result result = null;
		if (idStatus instanceof String) {
			try {
				result = Result.of(Integer.parseInt((String) idStatus));
			} catch (Exception ignored) {
				result = Result.valueOf((String) idStatus);
			}
		} else if (idStatus instanceof Result) {
			result = (Result) idStatus;
		}

		this.idStatus = result;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}
