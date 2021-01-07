package vn.metech.kafka.mbf;

public abstract class RequestKey {
    RequestKey() {

    }

    public static final String ACCOUNT_CODE = "account";
    public static final String REQUEST_ID = "requestId";
    public static final String CHECK_DATE = "datetime";
    public static final String Q_REQUEST_ID = "qRequestId";
    public static final String SECURE_CODE = "secureCode";
}
