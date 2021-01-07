package vn.metech.kafka.mbf;

public final class MbfApi {
	MbfApi() {

	}

	public static final String LOCATION_CHECKING = "/location/checking";
	public static final String CALL_REFERENCE_BASIC = "/callreference_basic";
	public static final String CALL_REFERENCE_ADVANCE = "/callreference_advance";
	public static final String ID_REFERENCE = "/idreference";
	public static final String AD_REFERENCE = "/adreference";

	//
	public static final String GET_RESULT_LIST = "/getSuccessRequest";
	public static final String GET_RESULT = "/getResponseByRequestId";
	public static final String GET_SUBSCRIBER_STATUS = "/subscriber/status";
	public static final String GET_MESSAGE_SENT = "/subscriber/getMT";
	public static final String GET_MESSAGE_RECEIVED = "/subscriber/getMO";
}
