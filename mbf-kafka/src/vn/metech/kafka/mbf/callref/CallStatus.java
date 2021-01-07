package vn.metech.kafka.mbf.callref;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.util.StringUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CallStatus implements Serializable {

	@JsonProperty("phonenumber")
	private String phoneNumber;

	@JsonProperty("refPhone1")
	private String refPhone1;

	@JsonProperty("refPhone1_status")
	private CallResult status1;

	@JsonProperty("refPhone2")
	private String refPhone2;

	@JsonProperty("refPhone2_status")
	private CallResult status2;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public String getRefPhone1() {
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
	}

	public CallResult getStatus1() {
		return status1;
	}

	public void setStatus1(Object status1) {
		if (status1 instanceof String) {
			try {
				int val = Integer.parseInt((String) status1);
				this.status1 = CallResult.valueOf(val);
			} catch (Exception ignored) {
				this.status1 = CallResult.valueOf((String) status1);
			}
		} else if (status1 instanceof CallResult) {
			this.status1 = (CallResult) status1;
		}
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
	}

	public CallResult getStatus2() {
		return status2;
	}

	public void setStatus2(Object status2) {
		if (status2 instanceof String) {
			try {
				int val = Integer.parseInt((String) status2);
				this.status2 = CallResult.valueOf(val);
			} catch (Exception ignored) {
				this.status2 = CallResult.valueOf((String) status2);
			}
		} else if (status2 instanceof CallResult) {
			this.status2 = (CallResult) status2;
		}
	}
}
