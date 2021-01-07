package vn.metech.kafka.mbf.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class MbfRegularlyLocation implements Serializable {

	@JsonProperty("day")
	private Integer day;

	@JsonProperty("hour")
	private Integer[] hours;

	@JsonProperty("refer")
	private Double[] referAddresses;

	@JsonProperty("home")
	private Double[] homeAddresses;

	@JsonProperty("work")
	private Double[] workAddresses;

	public MbfRegularlyLocation() {
		this.hours = new Integer[24];
		this.homeAddresses = new Double[24];
		this.workAddresses = new Double[24];
		this.referAddresses = new Double[24];
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer[] getHours() {
		return hours;
	}

	public void setHours(Integer[] hours) {
		this.hours = hours;
	}

	public Double[] getReferAddresses() {
		return referAddresses;
	}

	public void setReferAddresses(Double[] referAddresses) {
		this.referAddresses = referAddresses;
	}

	public Double[] getHomeAddresses() {
		return homeAddresses;
	}

	public void setHomeAddresses(Double[] homeAddresses) {
		this.homeAddresses = homeAddresses;
	}

	public Double[] getWorkAddresses() {
		return workAddresses;
	}

	public void setWorkAddresses(Double[] workAddresses) {
		this.workAddresses = workAddresses;
	}
}
