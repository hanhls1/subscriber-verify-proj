package vn.metech.dto.response;

import vn.metech.constant.MbfStatus;
import vn.metech.constant.ResponseStatus;
import vn.metech.dto.MbfLocationResult;
import vn.metech.dto.MostVisitLocation;
import vn.metech.dto.MtLocation;
import vn.metech.dto.RegularlyLocation;
import vn.metech.entity.LocationResponse;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MtAdvanceLocationResponse {

	private String responseId;
	private String phoneNumber;
	private String createdDate;
	private String dateToCheck;
	private MbfStatus partnerStatus;
	private MtLocation homeAddress;
	private MtLocation workAddress;
	private MtLocation referAddress;
	private ResponseStatus responseStatus;
	private List<MostVisitLocation> mostVisitLocations;
	private List<RegularlyLocation> regularlyLocations;

	public MtAdvanceLocationResponse() {
		this.mostVisitLocations = new ArrayList<>();
		this.regularlyLocations = new ArrayList<>();
	}

	public MtAdvanceLocationResponse(LocationResponse locationResponse) {
		this.responseId = locationResponse.getId();
		this.phoneNumber = locationResponse.getPhoneNumber();
		this.partnerStatus = locationResponse.getMbfStatus();
		this.createdDate = DateUtils.formatDate(locationResponse.getCreatedDate());
		this.dateToCheck = DateUtils.formatDate(locationResponse.getDateToCheck());
		this.responseStatus = locationResponse.getResponseStatus();
		this.homeAddress = JsonUtils.toObject(locationResponse.getHomeAddress(), MtLocation.class);
		this.workAddress = JsonUtils.toObject(locationResponse.getWorkAddress(), MtLocation.class);
		this.referAddress = JsonUtils.toObject(locationResponse.getRefAddress(), MtLocation.class);
		if (!StringUtils.isEmpty(locationResponse.getResponseData())) {
			MbfLocationResult result = JsonUtils.toObject(locationResponse.getResponseData(), MbfLocationResult.class);
			if (result != null) {
				this.regularlyLocations = RegularlyLocation.fromCollection(result.getRegularlyLocations());
				this.mostVisitLocations = MostVisitLocation.fromCollection(result.getMostVisitedLocations());
			}
		}
	}

	public static MtAdvanceLocationResponse from(LocationResponse locationResponse) {
		if (locationResponse == null) {
			return null;
		}

		return new MtAdvanceLocationResponse(locationResponse);
	}

	public static List<MtAdvanceLocationResponse> fromCollection(Collection<LocationResponse> locationResponses) {
		if (locationResponses == null || locationResponses.isEmpty()) {
			return Collections.emptyList();
		}

		return locationResponses.stream().map(MtAdvanceLocationResponse::from).collect(Collectors.toList());
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(String dateToCheck) {
		this.dateToCheck = dateToCheck;
	}

	public MbfStatus getPartnerStatus() {
		return partnerStatus;
	}

	public void setPartnerStatus(MbfStatus partnerStatus) {
		this.partnerStatus = partnerStatus;
	}

	public MtLocation getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(MtLocation homeAddress) {
		this.homeAddress = homeAddress;
	}

	public MtLocation getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(MtLocation workAddress) {
		this.workAddress = workAddress;
	}

	public MtLocation getReferAddress() {
		return referAddress;
	}

	public void setReferAddress(MtLocation referAddress) {
		this.referAddress = referAddress;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<MostVisitLocation> getMostVisitLocations() {
		return mostVisitLocations;
	}

	public void setMostVisitLocations(List<MostVisitLocation> mostVisitLocations) {
		this.mostVisitLocations = mostVisitLocations;
	}

	public List<RegularlyLocation> getRegularlyLocations() {
		return regularlyLocations;
	}

	public void setRegularlyLocations(List<RegularlyLocation> regularlyLocations) {
		this.regularlyLocations = regularlyLocations;
	}
}
