package vn.metech.dto.response.data;


import vn.metech.common.Result;

public class RefPhone2 extends CallBase {

	private String refPhone2;

	public RefPhone2() {
	}

	public RefPhone2(Result result, Double duration, Double frequency) {
		super(result, duration, frequency);
	}

	public RefPhone2(String refPhone2, Integer result, Double duration, Double frequency) {
		super(result, duration, frequency);
		this.refPhone2 = refPhone2;
	}

	public RefPhone2(String refPhone2, Result result, Double duration, Double frequency) {
		super(result == null ? null : result.value(), duration, frequency);
		this.refPhone2 = refPhone2;
	}

	public RefPhone2(String refPhone2, Integer result) {
		this(refPhone2, result, null, null);
	}

	public RefPhone2(String refPhone2, Result result) {
		this(refPhone2, result == null ? null : result.value(), null, null);
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
	}
}
