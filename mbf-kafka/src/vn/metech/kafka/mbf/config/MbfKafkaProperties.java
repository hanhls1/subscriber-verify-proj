package vn.metech.kafka.mbf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import vn.metech.kafka.config.KafkaTopic;

@ConfigurationProperties(prefix = "mbf.kafka")
public class MbfKafkaProperties {

	// request to mbf
	private KafkaTopic requestReceive;

	// response from mbf
	private KafkaTopic locationResponse;
	private KafkaTopic callResponse;
	private KafkaTopic idResponse;
	private KafkaTopic adResponse;


	public MbfKafkaProperties() {
		this.requestReceive = new KafkaTopic("request-receive");
		this.locationResponse = new KafkaTopic("location-response");
		this.callResponse = new KafkaTopic("call-response");
		this.idResponse = new KafkaTopic("id-response");
		this.adResponse = new KafkaTopic("ad-response");
	}

	public KafkaTopic getRequestReceive() {
		return requestReceive;
	}

	public void setRequestReceive(KafkaTopic requestReceive) {
		this.requestReceive = requestReceive;
	}

	public KafkaTopic getLocationResponse() {
		return locationResponse;
	}

	public void setLocationResponse(KafkaTopic locationResponse) {
		this.locationResponse = locationResponse;
	}

	public KafkaTopic getCallResponse() {
		return callResponse;
	}

	public void setCallResponse(KafkaTopic callResponse) {
		this.callResponse = callResponse;
	}

	public KafkaTopic getIdResponse() {
		return idResponse;
	}

	public void setIdResponse(KafkaTopic idResponse) {
		this.idResponse = idResponse;
	}

	public KafkaTopic getAdResponse() {
		return adResponse;
	}

	public void setAdResponse(KafkaTopic adResponse) {
		this.adResponse = adResponse;
	}
}
