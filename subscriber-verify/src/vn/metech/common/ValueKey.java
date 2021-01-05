package vn.metech.common;

public final class ValueKey {

	private ValueKey() {
	}

	public final static String DATA_FETCH_TIMES_LIMIT = "${data.fetch.times-limit:5}";
	public final static String DATA_FETCH_RECORD_LIMIT = "${data.fetch.record-limit:64}";
	public final static String DATA_FETCH_INTERVAL_IN_MINUTE = "${data.fetch.interval-in-minute:5}";
	//
	public final static String CONFIRM_INFO_REQUEST_HASH_KEY = "${confirm-info.request.hash-key}";
	//
	public final static String FEATURE_BALANCE_FLAG = "${feature.balance:false}";
	public final static String FEATURE_HASH_SECURITY_FLAG = "${feature.hash-security:false}";
	//
	public final static String PARTNER_MFS_REQUEST_URL = "${partner.mfs.request-url}";
	public final static String PARTNER_DWH_REQUEST_URL = "${partner.dwh.request-url}";
	//
	public final static String GPLACE_REQUEST_BASE_URL = "${place.gplace.base-url:https://maps.googleapis.com/maps/api}";
	public final static String GPLACE_REQUEST_API_KEY = "${place.gplace.api-key}";
	public final static String GPLACE_REQUEST_LANGUAGE = "${place.gplace.lang:vi}";
	public final static String GPLACE_RESPONSE_FIELDS = "${place.gplace.fields:formatted_address,name,geometry}";
}
