package vn.metech.kafka.mbf.subscriber;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.kafka.mbf.SubscriberStatus;
import vn.metech.util.DateUtils;

import java.util.Date;

public class SubscriberResult {

	@JsonProperty("status")
	private SubscriberStatus subscriberStatus;

	@JsonProperty("requestTime")
	private String requestTime;

	public SubscriberResult() {
	}

	public SubscriberResult(SubscriberStatus subscriberStatus, String requestTime) {
		this.subscriberStatus = subscriberStatus;
		this.requestTime = requestTime;
	}

	public SubscriberResult(SubscriberStatus subscriberStatus, Date requestTime) {
		this(subscriberStatus, DateUtils.formatDate(requestTime));
	}

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(Object subscriberStatus) {
		if (subscriberStatus instanceof Integer) {
			this.subscriberStatus = SubscriberStatus.of((int) subscriberStatus);
		} else if (subscriberStatus instanceof String) {
			this.subscriberStatus = SubscriberStatus.valueOf((String) subscriberStatus);
		} else if (subscriberStatus instanceof SubscriberStatus) {
			this.subscriberStatus = (SubscriberStatus) subscriberStatus;
		}
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
}
