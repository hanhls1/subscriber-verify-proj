package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Result;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallBase {

	@JsonProperty("status")
	private Integer result;

	@JsonProperty("duration")
	private Double duration;

	@JsonProperty("frequency")
	private Double frequency;

	public CallBase() {
	}

	public CallBase(Integer result, Double duration, Double frequency) {
		this();
		this.result = result;
		this.duration = duration;
		this.frequency = frequency;
	}

	public CallBase(Result result, Double duration, Double frequency) {
		this(result == null ? null : result.value(), duration, frequency);
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public void setResult(Result result) {
		this.result = result.value();
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}
}
