package vn.metech.kafka.mbf.adref;

import java.util.HashMap;
import java.util.Map;

public enum AdResult {

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

	AdResult(int value, String message) {
		this.value = value;
		this.message = message;
	}

	AdResult(int value) {
		this(value, null);
	}

	private static Map<Integer, AdResult> values = new HashMap<>();

	static {
		for (AdResult adResult : values()) {
			values.put(adResult.value, adResult);
		}
	}

	public static AdResult valueOf(int value) {
		AdResult status = values.get(value);

		return status == null ? UNDEFINED : status;
	}
}
