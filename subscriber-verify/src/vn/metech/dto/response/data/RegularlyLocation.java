package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegularlyLocation {

	@JsonProperty("day")
	private int day;

	@JsonProperty("location")
	private List<Location> locations;

	@JsonIgnore
	private Map<String, Location> locationRanges;

	public RegularlyLocation() {
		this.locations = new ArrayList<>();
		this.locationRanges = new HashMap<>();
	}

	public RegularlyLocation(int day, List<Location> locations) {
		this();
		this.day = day;
		this.locations = locations;
		if (locations != null) {
			locations.forEach(location -> locationRanges.put(location.getTimeRange(), location));
		}
	}

	public void addLocation(Location location) {
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Map<String, Location> getLocationRanges() {
		return locationRanges;
	}

	public void setLocationRanges(Map<String, Location> locationRanges) {
		this.locationRanges = locationRanges;
	}
}
