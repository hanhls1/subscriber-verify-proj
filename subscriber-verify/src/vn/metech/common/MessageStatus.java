package vn.metech.common;

public enum MessageStatus {
    UNKNOWN,
    CUSTOMER_PENDING("KH chưa xác nhận chia sẻ thông tin"),
    CUSTOMER_TIMEOUT("Hết hạn phản hồi tin nhắn"),
    REQUEST_SENT,
    ACCEPTED("KH đồng ý chia sẻ thông tin"),
    REJECTED("KH từ chối chia sẻ thông tin"),
    SYNTAX_ERROR("KH phản hồi sai cú pháp tin nhắn");

    String displayName;

    MessageStatus() {
        this("");
    }

    MessageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
