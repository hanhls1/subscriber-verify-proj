package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class MfsLocationFrequency {

	@JsonProperty("day")
	private int day;

	@JsonProperty("hour")
	private int[] hours;

	@JsonProperty("home")
	private double[] homeAddrs;

	@JsonProperty("work")
	private double[] workAddrs;

	@JsonProperty("refer")
	private double[] refAddrs;

	public MfsLocationFrequency() {
		this.hours = new int[24];
		this.homeAddrs = new double[24];
		this.workAddrs = new double[24];
		this.refAddrs = new double[24];
	}

	public List<Location> locations() {
		List<Location> locations = new ArrayList<>();
		locations.add(location(0, 7));
		locations.add(location(8, 12));
		locations.add(location(13, 18));
		locations.add(location(13, 18));
		locations.add(location(19, 23));

		return locations;
	}

	public Location location(int fromIndex, int toIndex) {
		double home = 0, work = 0, ref = 0;
		for (int i = fromIndex; i <= toIndex; i++) {
			home += homeAddrs[i];
			work += workAddrs[i];
			ref += refAddrs[i];
		}
		int numbers = toIndex - fromIndex + 1;
		return new Location(
						fromIndex + "h-" + toIndex + "h",
						round(home / numbers),
						round(work / numbers),
						round(ref / numbers)
		);
	}

	private double round(double n) {
		Assert.isTrue(n >= 0, "n must larger 0");
		return n == 0 ? 0 : Math.round(n * 10000D) / 10000D;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int[] getHours() {
		return hours;
	}

	public void setHours(int[] hours) {
		this.hours = hours;
	}

	public double[] getHomeAddrs() {
		return homeAddrs;
	}

	public void setHomeAddrs(double[] homeAddrs) {
		this.homeAddrs = homeAddrs;
	}

	public double[] getWorkAddrs() {
		return workAddrs;
	}

	public void setWorkAddrs(double[] workAddrs) {
		this.workAddrs = workAddrs;
	}

	public double[] getRefAddrs() {
		return refAddrs;
	}

	public void setRefAddrs(double[] refAddrs) {
		this.refAddrs = refAddrs;
	}
}
