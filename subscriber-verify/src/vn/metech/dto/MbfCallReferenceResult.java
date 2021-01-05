package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MbfCallReferenceResult implements Serializable {

	@JsonProperty("report2")
	private CallStatus callStatus;

	@JsonProperty("report1")
	private CallFrequency callFrequency;

	public CallStatus getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(CallStatus callStatus) {
		this.callStatus = callStatus;
	}

	public CallFrequency getCallFrequency() {
		return callFrequency;
	}

	public void setCallFrequency(CallFrequency callFrequency) {
		this.callFrequency = callFrequency;
	}
}
