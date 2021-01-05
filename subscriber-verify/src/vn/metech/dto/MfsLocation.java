package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsLocation {

	private String lat;
	private String lng;

	@JsonProperty("r")
	private int radius;

	public MfsLocation() {
		this.radius = 1000;
	}

	public MfsLocation(Double lat, Double lng) {
		this(String.valueOf(lat), String.valueOf(lng));
	}

	public MfsLocation(String lat, String lng) {
		this();
		this.lat = lat;
		this.lng = lng;
	}

	public MfsLocation(Double lat, Double lng, int radius) {
		this(lat, lng);
		this.radius = radius;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
