package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import vn.metech.dto.CallFrequency;
import vn.metech.dto.Result;
import vn.metech.util.StringUtils;

import java.io.Serializable;

public class MtCallVerifyResponse implements Serializable {

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("freq1")
	private String freq1;

	@JsonProperty("dur1")
	private String dur1;

	@JsonProperty("freq2")
	private String freq2;

	@JsonProperty("dur2")
	private String dur2;

	@JsonProperty("result1")
	private Result status1;

	@JsonProperty("result2")
	private Result status2;

	public MtCallVerifyResponse(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public MtCallVerifyResponse(CallFrequency callFrequency) {
		Assert.notNull(callFrequency, "[callFreq] is null");

		this.phoneNumber = callFrequency.getPhoneNumber();
		this.freq1 = callFrequency.getFreq1();
		this.freq2 = callFrequency.getFreq2();
		this.dur1 = callFrequency.getDur1();
		this.dur2 = callFrequency.getDur2();
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

	public String getFreq1() {
		return freq1;
	}

	public void setFreq1(String freq1) {
		this.freq1 = freq1;
	}

	public String getDur1() {
		return dur1;
	}

	public void setDur1(String dur1) {
		this.dur1 = dur1;
	}

	public String getFreq2() {
		return freq2;
	}

	public void setFreq2(String freq2) {
		this.freq2 = freq2;
	}

	public String getDur2() {
		return dur2;
	}

	public void setDur2(String dur2) {
		this.dur2 = dur2;
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
