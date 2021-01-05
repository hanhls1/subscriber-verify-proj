package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MbfAdReferenceResult implements Serializable {

	@JsonProperty("report1")
	private CurrentImeiResult currentImeiResult;

	@JsonProperty("report2")
	private LatestImeiResult latestImeiResult;

	public CurrentImeiResult getCurrentImeiResult() {
		return currentImeiResult;
	}

	public void setCurrentImeiResult(CurrentImeiResult currentImeiResult) {
		this.currentImeiResult = currentImeiResult;
	}

	public LatestImeiResult getLatestImeiResult() {
		return latestImeiResult;
	}

	public void setLatestImeiResult(LatestImeiResult latestImeiResult) {
		this.latestImeiResult = latestImeiResult;
	}
}
