package vn.metech.kafka.mbf.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.kafka.config.KafkaTopic;
import vn.metech.util.StringUtils;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@JsonInclude(NON_EMPTY)
public class RequestKafkaMsg implements Serializable {

	private String requestId;
	private String requestBy;
	private String requestUrl;
	private String customerRequestId;
	private String phoneNumber;
	private String customerCode;
	private String responseId;
	private KafkaTopic responseTopic;
	private String partnerStatusTopic;
	private Object requestBody;

	public static RequestMsgBuilder builder() {
		return new RequestMsgBuilder();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getCustomerRequestId() {
		return customerRequestId;
	}

	public void setCustomerRequestId(String customerRequestId) {
		this.customerRequestId = customerRequestId;
	}

	public String getPhoneNumber() {
		if (StringUtils.isEmpty(phoneNumber)) {
			return phoneNumber.trim().replaceAll("[^0-9]", "");
		}
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public KafkaTopic getResponseTopic() {
		return responseTopic;
	}

	public void setResponseTopic(KafkaTopic responseTopic) {
		this.responseTopic = responseTopic;
	}

	public String getPartnerStatusTopic() {
		return partnerStatusTopic;
	}

	public void setPartnerStatusTopic(String partnerStatusTopic) {
		this.partnerStatusTopic = partnerStatusTopic;
	}

	public Object getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(Object requestBody) {
		this.requestBody = requestBody;
	}
}
