package vn.metech.kafka.mbf.message;

import org.springframework.util.Assert;
import vn.metech.kafka.config.KafkaTopic;

public class RequestMsgBuilder {

	private RequestKafkaMsg _instance = new RequestKafkaMsg();

	public RequestMsgBuilder phoneNumber(String phoneNumber) {
		_instance.setPhoneNumber(phoneNumber);

		return this;
	}

	public RequestMsgBuilder requestId(String requestId) {
		_instance.setRequestId(requestId);

		return this;
	}

	public RequestMsgBuilder customerRequestId(String customerRequestId) {
		_instance.setCustomerRequestId(customerRequestId);

		return this;
	}

	public RequestMsgBuilder customerCode(String customerCode) {
		_instance.setCustomerCode(customerCode);

		return this;
	}

	public RequestMsgBuilder responseId(String responseId) {
		_instance.setResponseId(responseId);

		return this;
	}

	public RequestMsgBuilder requestBody(Object requestBody) {
		_instance.setRequestBody(requestBody);

		return this;
	}

	public RequestMsgBuilder responseTopic(KafkaTopic responseTopic) {
		_instance.setResponseTopic(responseTopic);

		return this;
	}

	public RequestMsgBuilder responseTopic(String responseTopic) {
		_instance.setResponseTopic(new KafkaTopic(responseTopic));

		return this;
	}

	public RequestMsgBuilder requestBy(String requestBy) {
		_instance.setRequestBy(requestBy);

		return this;
	}

	public RequestMsgBuilder requestUrl(String requestUrl) {
		_instance.setRequestUrl(requestUrl);

		return this;
	}

	public RequestMsgBuilder partnerStatusTopic(String partnerStatusTopic) {
		_instance.setPartnerStatusTopic(partnerStatusTopic);

		return this;
	}

	public RequestKafkaMsg build() {
		Assert.notNull(_instance.getPhoneNumber(), "phoneNumber is required");
		Assert.notNull(_instance.getCustomerCode(), "customerCode is required");
		Assert.notNull(_instance.getRequestId(), "requestId is required");
		Assert.notNull(_instance.getCustomerRequestId(), "customerRequestId is required");
		Assert.notNull(_instance.getRequestBody(), "body is required");
		Assert.notNull(_instance.getResponseTopic(), "responseTopic is required");
		Assert.notNull(_instance.getRequestBy(), "requestUser is required");
		Assert.notNull(_instance.getRequestUrl(), "requestUrl is required");
		Assert.notNull(_instance.getPartnerStatusTopic(), "partnerStatusTopic is required");

		return _instance;
	}
}
