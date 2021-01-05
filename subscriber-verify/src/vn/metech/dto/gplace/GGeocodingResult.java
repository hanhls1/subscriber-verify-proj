package vn.metech.dto.gplace;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GGeocodingResult {

	@JsonProperty("formatted_address")
	private String formattedAddr;

	public GGeocodingResult() {

	}

	public String getFormattedAddr() {
		return formattedAddr;
	}

	public void setFormattedAddr(String formattedAddr) {
		this.formattedAddr = formattedAddr;
	}
}
