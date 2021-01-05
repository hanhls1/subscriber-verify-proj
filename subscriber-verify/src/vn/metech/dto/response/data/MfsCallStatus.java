package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Result;

public class MfsCallStatus {

	@JsonProperty("refPhone1_status")
	private Result refPhone1Status;

	@JsonProperty("refPhone2_status")
	private Result refPhone2Status;

	public Result getRefPhone1Status() {
		return refPhone1Status;
	}

	public void setRefPhone1Status(Object refPhone1Status) {
		Result result = null;
		if (refPhone1Status instanceof String) {
			try {
				result = Result.of(Integer.parseInt((String) refPhone1Status));
			} catch (Exception ignored) {
				result = Result.valueOf((String) refPhone1Status);
			}
		} else if (refPhone1Status instanceof Result) {
			result = (Result) refPhone1Status;
		}

		this.refPhone1Status = result;
	}

	public Result getRefPhone2Status() {
		return refPhone2Status;
	}

	public void setRefPhone2Status(Object refPhone2Status) {
		Result result = null;
		if (refPhone2Status instanceof String) {
			try {
				result = Result.of(Integer.parseInt((String) refPhone2Status));
			} catch (Exception ignored) {
				result = Result.valueOf((String) refPhone2Status);
			}
		} else if (refPhone2Status instanceof Result) {
			result = (Result) refPhone2Status;
		}

		this.refPhone2Status = result;
	}
}
