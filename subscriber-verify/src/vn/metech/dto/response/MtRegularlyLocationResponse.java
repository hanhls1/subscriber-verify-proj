package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.MbfStatus;
import vn.metech.dto.MbfRegularlyLocation;
import vn.metech.dto.RegularlyLocation;
import vn.metech.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class MtRegularlyLocationResponse {

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
	private List<RegularlyLocation> regularlyLocations;

	public MtRegularlyLocationResponse() {
		this.regularlyLocations = new ArrayList<>();
		this.applyMbfStatus(MbfStatus.REQUESTED);
	}

	public MtRegularlyLocationResponse(String requestId, String responseId, String phoneNumber) {
		this();
		this.requestId = requestId;
		this.responseId = responseId;
		this.phoneNumber = phoneNumber;
	}

	public MtRegularlyLocationResponse(
					String requestId, String responseId, MbfStatus status,
					String phoneNumber, List<MbfRegularlyLocation> results) {
		this();
		this.requestId = requestId;
		this.responseId = responseId;
		this.phoneNumber = phoneNumber;

		this.applyMbfStatus(status);
		this.addRegularlyLocationResult(results);
	}

	public void addRegularlyLocationResult(List<MbfRegularlyLocation> results) {
		for (MbfRegularlyLocation regularlyLocation : results) {
			this.regularlyLocations.add(new RegularlyLocation(regularlyLocation));
		}
	}

	public void applyMbfStatus(MbfStatus status) {
		this.status = status.value();
		this.description = status.message();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RegularlyLocation> getRegularlyLocations() {
		return regularlyLocations;
	}

	public void setRegularlyLocations(List<RegularlyLocation> regularlyLocations) {
		this.regularlyLocations = regularlyLocations;
	}
}
