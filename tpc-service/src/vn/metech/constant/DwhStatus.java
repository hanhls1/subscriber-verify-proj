package vn.metech.constant;

import java.util.HashMap;
import java.util.Map;

public enum DwhStatus {

	REQUESTED(-3, "Sent question"),
	TIMEOUT(-2, "Request Timeout"),
	UNDEFINED(-1, "Status not defined"),

	SUCCESS(200, "thanh công có dữ liệu trả về"),
	DATA_NOT_FOUND(201, "Không map được thông tin - không có dữ liệu trả về"),
	DATA_NOT_FOUND_2(204, "Không map được thông tin - không có dữ liệu trả về"),
	REQUEST_ERROR(400, "Lỗi request"),
	PROCESS_ERROR(500, "Lỗi trong quá trình xử lý");

	private int value;
	private String message;

	public int value() {
		return this.value;
	}

	public String message() {
		return this.message;
	}

	DwhStatus(int value, String message) {
		this.value = value;
		this.message = message;
	}

	DwhStatus(int value) {
		this(value, null);
	}

	private static Map<Integer, DwhStatus> values = new HashMap<>();

	static {
		for (DwhStatus dwhStatus : values()) {
			values.put(dwhStatus.value, dwhStatus);
		}
	}

	public static DwhStatus valueOf(int value) {
		if (value >= 400) {
			String val = String.valueOf(value);
			if (val.startsWith("4")) {
				value = 400;
			} else if (val.startsWith("5")) {
				value = 500;
			}
		}

		DwhStatus status = values.get(value);

		return status == null ? UNDEFINED : status;
	}

}
