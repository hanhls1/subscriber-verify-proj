package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.util.StringUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CallFrequency implements Serializable {

	@JsonProperty("phonenumber")
	private String phoneNumber;

	@JsonProperty("refPhone1_freq")
	private String freq1;

	@JsonProperty("refPhone1_dur")
	private String dur1;

	@JsonProperty("refPhone2_freq")
	private String freq2;

	@JsonProperty("refPhone2_dur")
	private String dur2;

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
}
