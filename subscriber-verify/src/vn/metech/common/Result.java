package vn.metech.common;

import java.util.HashMap;
import java.util.Map;

public enum Result {

	NO_RESULT(0),
	MATCH(1),
	NOT_MATCH(2)
	//
	;

	int value;

	Result(int value) {
		this.value = value;
	}

	static Map<Integer, Result> results = new HashMap<>();

	static {
		for (Result result : values()) {
			results.put(result.value, result);
		}
	}

	public int value() {
		return this.value;
	}

	public static Result of(int val) {
		return results.get(val);
	}
}
