package vn.metech.kafka.mbf.idref;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MbfIdReferenceResult implements Serializable {

	@JsonProperty("report2")
	private CurrentIdNumber currentIdNumber;

	@JsonProperty("report1")
	private PastIdNumber pastIdNumber;

	public CurrentIdNumber getCurrentIdNumber() {
		return currentIdNumber;
	}

	public void setCurrentIdNumber(CurrentIdNumber currentIdNumber) {
		this.currentIdNumber = currentIdNumber;
	}

	public PastIdNumber getPastIdNumber() {
		return pastIdNumber;
	}

	public void setPastIdNumber(PastIdNumber pastIdNumber) {
		this.pastIdNumber = pastIdNumber;
	}
}
