package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Result;

public class MfsLatestImeiStatus {

	@JsonProperty("resultImei2")
	private Result imeiStatus;

	@JsonProperty("dateCheckImei")
	private String checkDate;

	public Result getImeiStatus() {
		return imeiStatus;
	}

	public void setImeiStatus(Object imeiStatus) {
		Result result = null;
		if (imeiStatus instanceof String) {
			try {
				result = Result.of(Integer.parseInt((String) imeiStatus));
			} catch (Exception ignored) {
				result = Result.valueOf((String) imeiStatus);
			}
		} else if (imeiStatus instanceof Result) {
			result = (Result) imeiStatus;
		}

		this.imeiStatus = result;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}
