package vn.metech.common;

import java.util.HashMap;
import java.util.Map;

public enum Param {

	PHONE_NUMBER("PHONE_NUMBER"),
	PROVINCE("PROVINCE"),
	REQUEST_ID("REQUEST_ID"),
	HOME_ADDRESS("HOME_ADDRESS"),
	WORK_ADDRESS("WORK_ADDRESS"),
	REF_ADDRESS("REF_ADDRESS"),
	CHECK_DATE("CHECK_DATE"),
	REF_PHONE_1("REF_PHONE_1"),
	REF_PHONE_2("REF_PHONE_2"),
	ID_NUMBER("ID_NUMBER"),
	CUSTOMER_NAME("CUSTOMER_NAME"),
	LOAN_ID("LOAN_ID"),
	NUMBER_OF_DEPENDENCY("NUMBER_OF_DEPENDENCY"),
	EMPLOYER_NAME("EMPLOYER_NAME")
	//
	;

	String key;

	static Map<String, Param> params = new HashMap<>();

	static {
		for (Param param : values()) {
			params.put(param.key, param);
		}
	}

	Param(String key) {
		this.key = key;
	}

	public static Param of(String key) {
		return params.get(key);
	}

	public String key() {
		return key;
	}
}
