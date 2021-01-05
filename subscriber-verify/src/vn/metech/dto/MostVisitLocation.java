package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MostVisitLocation implements Serializable {

	@JsonProperty("lat")
	private Double lat;

	@JsonProperty("lng")
	private Double lng;

	@JsonProperty("frequency")
	private Double frequency;

	public MostVisitLocation() {
	}

	public MostVisitLocation(MbfMostVisitLocation location) {
		this(location.getLat(), location.getLng(), location.getPercent());
	}

	public MostVisitLocation(Double lat, Double lng, Double frequency) {
		this();
		this.lat = lat;
		this.lng = lng;
		this.frequency = frequency;
	}

	public static MostVisitLocation from(MbfMostVisitLocation mbfMostVisitLocation) {
		if (mbfMostVisitLocation == null) {
			return null;
		}

		MostVisitLocation mostVisitLocation = new MostVisitLocation();
		mostVisitLocation.lat = mbfMostVisitLocation.getLat();
		mostVisitLocation.lng = mbfMostVisitLocation.getLng();
		mostVisitLocation.frequency = mbfMostVisitLocation.getPercent();

		return mostVisitLocation;
	}

	public static List<MostVisitLocation> fromCollection(Collection<MbfMostVisitLocation> mostVisitedLocations) {
		if (mostVisitedLocations == null || mostVisitedLocations.isEmpty()) {
			return Collections.emptyList();
		}

		return mostVisitedLocations.stream().map(MostVisitLocation::from).collect(Collectors.toList());
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}
}
