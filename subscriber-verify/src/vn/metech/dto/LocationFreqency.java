package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationFreqency implements Serializable {

	@JsonProperty("address")
	private String address;

	@JsonProperty("fromHour")
	private Integer from;

	@JsonProperty("toHour")
	private Integer to;

	@JsonProperty("percent")
	private Double percent;

	@JsonIgnore
	private List<Integer> hours;

	@JsonIgnore
	private List<Double> values;

	public LocationFreqency() {
		this.values = new ArrayList<>();
		this.hours = new ArrayList<>();
	}

	public LocationFreqency(int from, int to, double percent) {
		this();
		this.from = from;
		this.to = to;
		this.percent = percent;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getFrom() {
		if (hours.size() == 0) {
			return from;
		}

		return hours.get(0);
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		if (hours.size() == 0) {
			return to;
		}

		int to = hours.get(hours.size() - 1) + 1;

		return to >= 24 ? 23 : to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Double getPercent() {
		if (values.size() == 0) {
			return percent;
		}

		double total = 0;
		for (Double val : values) {
			total += val;
		}

		double avg = Math.round(total * 100 / values.size());

		return avg / 100;
	}

	public List<Integer> getHours() {
		return hours;
	}

	public List<Double> getValues() {
		return values;
	}
}
