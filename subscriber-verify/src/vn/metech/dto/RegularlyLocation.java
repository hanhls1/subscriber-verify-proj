package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegularlyLocation implements Serializable {

	@JsonProperty("day")
	private Integer day;

	@JsonProperty("home")
	private LocationFreqency homeAddress;

	@JsonProperty("work")
	private LocationFreqency workAddress;

	@JsonProperty("refer")
	private LocationFreqency referAddress;

	public RegularlyLocation() {
	}

	public RegularlyLocation(MbfRegularlyLocation result) {
		Assert.notNull(result, "[result] is null");

		day = result.getDay();
		homeAddress = getOrDefault(result.getDay(), result.getHours(), result.getHomeAddresses());
		workAddress = getOrDefault(result.getDay(), result.getHours(), result.getWorkAddresses());
		referAddress = getOrDefault(result.getDay(), result.getHours(), result.getReferAddresses());
	}

	public static RegularlyLocation from(MbfRegularlyLocation regularlyLocation) {
		if (regularlyLocation == null) {
			return null;
		}

		return new RegularlyLocation(regularlyLocation);
	}

	public static List<RegularlyLocation> fromCollection(Collection<MbfRegularlyLocation> regularlyLocations) {
		if (regularlyLocations == null || regularlyLocations.isEmpty()) {
			return Collections.emptyList();
		}

		return regularlyLocations.stream().map(RegularlyLocation::from).collect(Collectors.toList());
	}

	private LocationFreqency getOrDefault(Integer day, Integer[] hours, Double[] data) {
		List<LocationFreqency> locations = exportRanges(day, hours, data);
		if (locations.size() > 0) {
			return locations.get(0);
		}

		return new LocationFreqency(0, 23, 0);
	}

	private List<LocationFreqency> exportRanges(Integer day, Integer[] hours, Double[] data) {
		List<LocationFreqency> locations = new ArrayList<>();
		LocationFreqency location = new LocationFreqency();
		for (int i = 0; i < data.length; i++) {
			double current = data[i];
			if (current == 0 && location.getHours().size() == 0) continue;

			// exception: last element
			if (i == data.length - 1) {
				double before = data[i - 1];
				if (current != 0 || before != 0) {
					location.getHours().add(hours[i]);
					location.getValues().add(current);
					locations.add(location);
				}
				break;
			}

			double after = data[i + 1];
			if (current != 0 || after != 0) {
				location.getHours().add(hours[i]);
				location.getValues().add(current);

				continue;
			}

			if (location.getHours().size() > 0) {
				locations.add(location);
				location = new LocationFreqency();
			}
		}
		locations.sort((before, after) -> after.getHours().size() - before.getHours().size());

		return locations;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public LocationFreqency getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(LocationFreqency homeAddress) {
		this.homeAddress = homeAddress;
	}

	public LocationFreqency getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(LocationFreqency workAddress) {
		this.workAddress = workAddress;
	}

	public LocationFreqency getReferAddress() {
		return referAddress;
	}

	public void setReferAddress(LocationFreqency referAddress) {
		this.referAddress = referAddress;
	}
}
