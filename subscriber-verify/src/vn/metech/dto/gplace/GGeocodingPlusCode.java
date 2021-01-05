package vn.metech.dto.gplace;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GGeocodingPlusCode {

	@JsonProperty("global_code")
	private String globalCode;

	@JsonProperty("compound_code")
	private String compoundCode;

	public String getGlobalCode() {
		return globalCode;
	}

	public void setGlobalCode(String globalCode) {
		this.globalCode = globalCode;
	}

	public String getCompoundCode() {
		return compoundCode;
	}

	public void setCompoundCode(String compoundCode) {
		this.compoundCode = compoundCode;
	}
}
