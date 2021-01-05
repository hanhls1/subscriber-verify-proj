package vn.metech.dto.response.data;

public class MostVisitLocation {

	private String address;
	private double percent;

	public MostVisitLocation() {
	}

	public MostVisitLocation(String address, double percent) {
		this.address = address;
		this.percent = percent;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
}
