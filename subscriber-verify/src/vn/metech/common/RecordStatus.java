package vn.metech.common;

public enum RecordStatus {

    PENDING("Đã tạo yêu cầu"),
    REQUESTING("Đang xử lý yêu cầu"),
    INVALID("Đã tạo yêu cầu"),
    READY("Đang xử lý yêu cầu"),
    NO_DATA("Đang xử lý yêu cầu"),
    NO_FETCH_DATA("Đang xử lý yêu cầu"),
    NOT_SEND("Đã tạo yêu cầu"),
    ERROR("Đã tạo yêu cầu");

    String displayName;

    RecordStatus() {
        this("");
    }

    RecordStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
