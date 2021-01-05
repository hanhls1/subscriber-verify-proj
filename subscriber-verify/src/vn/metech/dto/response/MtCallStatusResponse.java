package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import vn.metech.dto.CallStatus;
import vn.metech.dto.Result;
import vn.metech.util.StringUtils;

import java.io.Serializable;

public class MtCallStatusResponse implements Serializable {

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("status1")
	private Result status1;

	@JsonProperty("status2")
	private Result status2;

	public MtCallStatusResponse(CallStatus callStatus) {
		Assert.notNull(callStatus, "[callStatus] is null");

		this.phoneNumber = callStatus.getPhoneNumber();
		this.status1 = callStatus.getStatus1();
		this.status2 = callStatus.getStatus2();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public Result getStatus1() {
		return status1;
	}

	public void setStatus1(Result status1) {
		this.status1 = status1;
	}

	public Result getStatus2() {
		return status2;
	}

	public void setStatus2(Result status2) {
		this.status2 = status2;
	}
}
