package vn.metech.common;

import java.util.HashMap;
import java.util.Map;

public enum MfsStatus {

	REQUESTED(-3, "Sent question"),
	TIMEOUT(-2, "Request Timeout"),
	UNDEFINED(-1, "Status not defined"),
	//
	SUCCESS(0, "Thanh cong"),
	MBF_BUSY(1, "He thong ban"),
	UNKNOWN_REQUEST(2, "Unknown request"),
	PARAM_INVALID(3, "Tham so request khong hop le"),
	ACCOUNT_NOT_FOUND(4, "Account khong ton tai tren he thong"),
	SECRET_KEY_INVALID(5, "Ma bao mat moi phai co 6 ky tu"),
	REQUEST_DUPLICATE(6, "RequestId da ton tai"),
	ACCOUNT_INACTIVE(7, "Account khong active"),
	SECURE_CODE_INVALID(8, "Ma secure code khong hop le"),
	ACCOUNT_UN_AUTHORIZED(9, "Account khong duoc phep truy cap API"),
	BALANCE_NOT_ENOUGH(10, "Tai khoan khong du"),
	SUBSCRIBER_NOT_ACCEPT(11, "So thue bao chua xac nhan cung cap thong tin"),
	MBF_RECORD_NOT_FOUND(12, "Khong ton tai ban ghi du lieu phan tich"),
	API_LOCKED(13, "API bi khoa"),
	PROCESS_ERROR(14, "Xu ly yeu cau bi loi"),
	CUSTOMER_CODE_NOT_FOUND(17, "Ma TCTC khong ton tai"),
	CUSTOMER_CODE_INVALID(18, "Ma TCTC khong thuoc quan ly cua Dai ly"),
	DECREASE_BALANCE_ERROR(19, "Khong the tru tien"),
	WAITING_ACCEPT_OR_REJECTED(20, "So lien lac dang cho xac nhan hoac tu choi cung cap thong tin"),
	REQUEST_ID_NOT_FOUND(21, "Ma Request truy van khong ton tai"),
	SUBSCRIBER_DENIED(100, "Khach hang huy / tu choi cung cap thong tin"),
	DATA_PROCESS_ERROR(101, "Co loi trong qua trinh xu ly du lieu"),
	CANNOT_SEND_SMS(102, "Khong the gui MT yeu cau xac nhan den thue bao"),
	SUBSCRIBER_NOT_REPLY(103, "Khach hang khong phan hoi yeu cau cung cap thong tin");

	private int value;
	private String message;

	public int value() {
		return this.value;
	}

	public String message() {
		return this.message;
	}

	MfsStatus(int value, String message) {
		this.value = value;
		this.message = message;
	}

	MfsStatus(int value) {
		this(value, null);
	}

	private static Map<Integer, MfsStatus> values = new HashMap<>();

	static {
		for (MfsStatus mfsStatus : values()) {
			values.put(mfsStatus.value, mfsStatus);
		}
	}

	public static MfsStatus of(int value) {
		MfsStatus status = values.get(value);

		return status == null ? UNDEFINED : status;
	}

}
