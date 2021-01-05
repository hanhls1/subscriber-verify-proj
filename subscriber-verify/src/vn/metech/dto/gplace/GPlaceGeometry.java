package vn.metech.dto.gplace;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GPlaceGeometry {

	@JsonProperty("location")
	private GPlaceLocation location;

	@JsonProperty("viewport")
	private Map<String, Object> metadata;

	public GPlaceLocation getLocation() {
		return location;
	}

	public void setLocation(GPlaceLocation location) {
		this.location = location;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
