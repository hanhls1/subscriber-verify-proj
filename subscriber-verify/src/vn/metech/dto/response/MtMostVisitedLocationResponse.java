package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.MbfStatus;
import vn.metech.dto.MbfMostVisitLocation;
import vn.metech.dto.MostVisitLocation;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MtMostVisitedLocationResponse implements Serializable {

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("responseId")
	private String responseId;

	@JsonProperty("status")
	private Integer status;

	@JsonProperty("desc")
	private String description;

	@JsonProperty("data")
	private List<MostVisitLocation> mostVisitedLocations;

	public MtMostVisitedLocationResponse() {
		this.mostVisitedLocations = new ArrayList<>();
		this.applyMbfStatus(MbfStatus.REQUESTED);
	}

	public MtMostVisitedLocationResponse(
					String requestId, String responseId, MbfStatus status,
					String phoneNumber, List<MbfMostVisitLocation> locations) {
		this();

		this.requestId = requestId;
		this.responseId = responseId;
		this.phoneNumber = phoneNumber;

		this.applyMbfStatus(status);
		this.addMostVisitedLocationResult(locations);
	}

	public MtMostVisitedLocationResponse(
					String requestId, String responseId, String phoneNumber) {
		this();
		this.requestId = requestId;
		this.responseId = responseId;
		this.phoneNumber = phoneNumber;
	}

	public void applyMbfStatus(MbfStatus mbfStatus) {
		this.status = mbfStatus.value();
		this.description = mbfStatus.message();
	}

	public void addMostVisitedLocationResult(List<MbfMostVisitLocation> locations) {
		for (MbfMostVisitLocation location : locations) {
			this.mostVisitedLocations.add(new MostVisitLocation(location));
		}
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public List<MostVisitLocation> getMostVisitedLocations() {
		return mostVisitedLocations;
	}

	public void setMostVisitedLocations(List<MostVisitLocation> mostVisitedLocations) {
		this.mostVisitedLocations = mostVisitedLocations;
	}
}
