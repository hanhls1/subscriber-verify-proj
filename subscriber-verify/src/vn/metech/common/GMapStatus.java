package vn.metech.common;

public enum GMapStatus {

	OK("indicates that no errors occurred; the place was successfully detected and at least one result was returned."),
	ZERO_RESULTS("indicates that the search was successful but returned no results. " +
					"This may occur if the search was passed a latlng in a remote location."),
	OVER_QUERY_LIMIT("indicates that you are over your quota."),
	REQUEST_DENIED("indicates that your request was denied, generally because of lack of an invalid key parameter."),
	INVALID_REQUEST("generally indicates that a required query parameter (location or radius) is missing."),
	UNKNOWN_ERROR("indicates a server-side error; trying again may be successful.")

	//
	;

	GMapStatus() {
		this("");
	}

	GMapStatus(String desc) {
		this.desc = desc;
	}

	private String desc;

	public String desc() {
		return this.desc;
	}
}
