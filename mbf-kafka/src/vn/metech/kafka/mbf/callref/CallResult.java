package vn.metech.kafka.mbf.callref;

import java.util.HashMap;
import java.util.Map;

public enum CallResult {

	UNDEFINED(-1, "Undefined result"),

	NO_RESULT(0, "No result"),
	MATCH(1, "Match"),
	NOT_MATCH(2, "Not match");

	private int value;
	private String message;

	public int value() {
		return this.value;
	}

	public String message() {
		return this.message;
	}

	CallResult(int value, String message) {
		this.value = value;
		this.message = message;
	}

	CallResult(int value) {
		this(value, null);
	}

	private static Map<Integer, CallResult> values = new HashMap<>();

	static {
		for (CallResult adResult : values()) {
			values.put(adResult.value, adResult);
		}
	}

	public static CallResult valueOf(int value) {
		CallResult status = values.get(value);

		return status == null ? UNDEFINED : status;
	}
}
