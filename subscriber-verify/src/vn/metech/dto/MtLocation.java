package vn.metech.dto;

public class MtLocation {

	private String address;
	private String lat;
	private String lng;
	private int r;

	public MtLocation() {
		r = 3912;
	}

	public MtLocation(String address, String lat, String lng, int r) {
		this();
		this.lat = lat;
		this.lng = lng;
		this.r = r;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
}
