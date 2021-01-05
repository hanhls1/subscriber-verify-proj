package vn.metech.common;

import java.util.HashMap;
import java.util.Map;

public enum StatusCode {

    OK(0, "Thành Công"),
    PARAM_INVALID(1, "Tham số không đủ hoặc không hợp lệ"),
    REQUEST_FAILED(2, "Xảy ra lỗi khi request"),
    REQUEST_REFUSED(3, "Request bị từ chối"),
    REQUEST_NOT_FOUND(4, "Request không tồn tại hoặc không có kết quả"),
    UNDEFINDED(5, "Lỗi chưa xác định"),
    REQUEST_MANY(501, "Yêu cầu này đã kiểm tra trong (x) ngày/giờ, vui lòng thử lại");

    int value;
    String description;

    static Map<Integer, StatusCode> statusCodes = new HashMap<>();

    static {
        for (StatusCode statusCode : values()) {
            statusCodes.put(statusCode.value, statusCode);
        }
    }

    public static StatusCode of(int value) {
        StatusCode statusCode = statusCodes.get(value);

        return statusCode == null ? UNDEFINDED : statusCode;
    }

    StatusCode(int value) {
        this(value, null);
    }

    StatusCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return value;
    }

    public String description() {
        return description;
    }
}
