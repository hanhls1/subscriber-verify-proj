package vn.metech.constant;

import java.util.HashMap;
import java.util.Map;

public enum VerifyService {

	FC01("FC01", "So sánh CMND/CCCD trong quá khứ"),

	FC02_01("FC02.01", "So sánh IMEI trong quá khứ"),
	FC02_02("FC02.02", "So sánh 2 IMEI gần nhất trong vòng 30 ngày"),

	FC03_01("FC03.01", "Thông tin tần suất xuất hiện trong tuần, tháng, quý gần nhất"),
	FC03_01_VPB("FC03.01.VPB", "Thông tin tần suất xuất hiện trong tuần, tháng, quý gần nhất"),
	FC03_02("FC03.02", "Xác nhận tỉnh/thành phố"),

	FC04_01("FC04.01", "Thông tin tần suất cuộc gọi & thời lượng cuộc gọi trong 3 tháng gần nhất"),
	FC04_01_VPB("FC04.01.VPB", "Thông tin tần suất cuộc gọi & thời lượng cuộc gọi trong 3 tháng gần nhất"),
	FC04_02("FC04.02", "So sánh top 20 liên hệ trong vòng 30 ngày"),

	KYC02("KYC02"),
	KYC02_TPC("KYC02.TPC"),
	KYC02_VPB("KYC02.VPB"),

	KYC03("KYC03", "Tần suất tại 3 địa điểm xuất hiện nhiều nhất trong 30 ngày")
	//
	;

	String name;
	String desc;

	VerifyService(String name) {
		this(name, null);
	}

	VerifyService(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	static Map<String, VerifyService> services = new HashMap<>();

	static {
		for (VerifyService verifyService : values()) {
			services.put(verifyService.name, verifyService);
		}
	}

	public static VerifyService of(String name) {
		return services.get(name);
	}
}
