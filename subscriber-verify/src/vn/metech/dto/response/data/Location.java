package vn.metech.dto.response.data;

public class Location {

	private String timeRange;
	private double homePercent;
	private double workPercent;
	private double refPercent;

	public Location() {
		this.homePercent = 0;
		this.workPercent = 0;
		this.refPercent = 0;
	}

	public Location(String timeRange, double homePercent, double workPercent) {
		this(timeRange, homePercent, workPercent, 0);
	}

	public Location(String timeRange, double homePercent, double workPercent, double refPercent) {
		this.timeRange = timeRange;
		this.homePercent = homePercent;
		this.workPercent = workPercent;
		this.refPercent = refPercent;
	}

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public double getHomePercent() {
		return homePercent;
	}

	public void setHomePercent(double homePercent) {
		this.homePercent = homePercent;
	}

	public double getWorkPercent() {
		return workPercent;
	}

	public void setWorkPercent(double workPercent) {
		this.workPercent = workPercent;
	}

	public double getRefPercent() {
		return refPercent;
	}

	public void setRefPercent(double refPercent) {
		this.refPercent = refPercent;
	}
}
