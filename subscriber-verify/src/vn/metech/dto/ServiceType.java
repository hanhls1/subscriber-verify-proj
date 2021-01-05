package vn.metech.dto;

public enum ServiceType {

	FC_ADV_CAC("Xác thực vị trí nâng cao"),
	FC_REF_PHONE("Xác thực SĐT tham chiếu"),
	FC_ADV_REF_PHONE("Xác thực SĐT tham chiếu"),
	FC_IMEI("Xác thực thiết bị"),
	FC_ID("Xác thực CMND/CCCD"),
	FC_KYC02_VPB("Tìm kiếm và xử lý dữ liệu");

	String displayName;

	ServiceType(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() {
		return this.displayName;
	}
}
