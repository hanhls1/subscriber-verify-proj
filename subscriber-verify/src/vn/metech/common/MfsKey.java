package vn.metech.common;

public enum MfsKey {

	REQUEST_ID("requestId"),
	PHONE_NUMBER("phonenumber"),
	ACCOUNT("account"),
	SECURE_CODE("secureCode"),
	CUSTOMER_CODE("customerCode"),
	LOCATION_CHECK_DATE("datetime"),
	WORK_ADDRESS("workaddress"),
	HOME_ADDRESS("homeaddress"),
	REFERENCE_ADDRESS("referaddress"),
	LOCATION_TOTAL_DATE("totaldate"),
	REF_PHONE_1("refPhone1"),
	REF_PHONE_2("refPhone2"),
	IMEI_CHECK_DATE("dateCheckImei"),
	ID_NUMBER("id"),
	ID_CHECK_DATE("dateCheckId"),
	IMEI_NUMBER("imei")
	//
	;

	String key;

	MfsKey(String key) {
		this.key = key;
	}

	public String key() {
		return this.key;
	}
}
