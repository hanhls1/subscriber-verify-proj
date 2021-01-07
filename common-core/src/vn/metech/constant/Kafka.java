package vn.metech.constant;

public final class Kafka {
	private Kafka() {
	}

	public static final String CUSTOMER_REPLY_TOPIC_KEY = "${kafka.topic.customer-reply.topics:customer-reply}";
	public static final String CUSTOMER_REPLY_GROUP_KEY = "${kafka.topic.customer-reply.group-id:default}";

	public static final String CUSTOMER_RESULT_TOPIC_KEY = "${kafka.topic.customer-result.topics:customer-result}";
	public static final String CUSTOMER_RESULT_GROUP_KEY = "${kafka.topic.customer-result.group-id:default}";

	public static final String PAYMENT_BALANCE_TOPIC_KEY = "${kafka.topic.payment-balance.topics:payment-balance}";
	public static final String PAYMENT_BALANCE_TOPIC_GROUP_KEY = "${kafka.topic.payment-balance.group-id:default}";

}
