package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.common.Result;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefPhone1 extends CallBase {

	private String refPhone1;

	public RefPhone1() {
	}

	public RefPhone1(Result result, Double duration, Double frequency) {
		super(result, duration, frequency);
	}

	public RefPhone1(String refPhone1, Integer result, Double duration, Double frequency) {
		super(result, duration, frequency);
		this.refPhone1 = refPhone1;
	}

	public RefPhone1(String refPhone1, Result result, Double duration, Double frequency) {
		super(result == null ? null : result.value(), duration, frequency);
		this.refPhone1 = refPhone1;
	}

	public RefPhone1(String refPhone1, Integer result) {
		this(refPhone1, result, null, null);
	}

	public RefPhone1(String refPhone1, Result result) {
		this(refPhone1, result == null ? null : result.value(), null, null);
	}

	public String getRefPhone1() {
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
	}
}
