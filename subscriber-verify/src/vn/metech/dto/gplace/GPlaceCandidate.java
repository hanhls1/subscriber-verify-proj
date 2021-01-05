package vn.metech.dto.gplace;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GPlaceCandidate {

	@JsonProperty("formatted_address")
	private String formattedAddr;

	@JsonProperty("name")
	private String addrName;

	@JsonProperty("geometry")
	private GPlaceGeometry geometry;

	public String getFormattedAddr() {
		return formattedAddr;
	}

	public void setFormattedAddr(String formattedAddr) {
		this.formattedAddr = formattedAddr;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public GPlaceGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(GPlaceGeometry geometry) {
		this.geometry = geometry;
	}
}
