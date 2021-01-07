package vn.metech.kafka.mbf;

import java.util.HashMap;
import java.util.Map;

public enum SubscriberStatus {

	NO_STATUS(-2, "Chưa có trạng thái"),
	PENDING(-1, "Chưa gửi yêu cầu xác nhận tới khách hàng"),
	SMS_SENT(0, "Đã gửi yêu cầu xác nhận tới khách hàng, khách hàng chưa xác nhận"),
	ACCEPTED(1, "Khách hàng đã đồng ý"),
	CANCELED(2, "Khách hàng hủy xác nhận"),
	REJECTED(3, "Khách hàng từ chối"),
	TIMEOUT(4, "Khách hàng xác nhận nhưng thời gian hiệu lực đã hết (quá 30 ngày kể từ thời điểm xác nhận)"),
	SMS_SYNTAX(5, "Tin nhắn sai cú pháp"),
	;

	SubscriberStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	SubscriberStatus(int code) {
		this(code, "");
	}

	static final Map<Integer, SubscriberStatus> statuses = new HashMap<>();

	static {
		for (SubscriberStatus subscriberStatus : values()) {
			statuses.put(subscriberStatus.code, subscriberStatus);
		}
	}

	int code;
	String desc;

	public int code() {
		return code;
	}

	public String desc() {
		return desc;
	}

	public static SubscriberStatus of(int code) {
		SubscriberStatus status = statuses.get(code);
		return status != null ? status : PENDING;
	}
}
