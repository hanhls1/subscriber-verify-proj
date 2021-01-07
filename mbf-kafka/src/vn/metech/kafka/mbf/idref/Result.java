package vn.metech.kafka.mbf.idref;

import java.util.HashMap;
import java.util.Map;

public enum Result {

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

	Result(int value, String message) {
		this.value = value;
		this.message = message;
	}

	Result(int value) {
		this(value, null);
	}

	private static Map<Integer, Result> values = new HashMap<>();

	static {
		for (Result adResult : values()) {
			values.put(adResult.value, adResult);
		}
	}

	public static Result valueOf(int value) {
		Result status = values.get(value);

		return status == null ? UNDEFINED : status;
	}
}
