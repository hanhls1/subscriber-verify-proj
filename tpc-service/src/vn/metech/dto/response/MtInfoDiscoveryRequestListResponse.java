package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import vn.metech.constant.RequestStatus;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MtInfoDiscoveryRequestListResponse implements Serializable {

	@JsonProperty("requestId")
	private String requestId;

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("idNumber")
	private String idNumber;

	@JsonProperty("requestStatus")
	private RequestStatus requestStatus;

	@JsonProperty("createdDate")
	private String createdDate;

	public static MtInfoDiscoveryRequestListResponse from(InfoDiscoveryRequest infoDiscoveryRequest) {
		Assert.notNull(infoDiscoveryRequest, "[infoDiscoveryRequest is null]");

		MtInfoDiscoveryRequestListResponse response = new MtInfoDiscoveryRequestListResponse();
		response.requestId = infoDiscoveryRequest.getId();
		response.idNumber = infoDiscoveryRequest.getIdNumber();
		response.requestStatus = infoDiscoveryRequest.getRequestStatus();
		response.setCreatedDate(infoDiscoveryRequest.getCreatedDate());

		return response;
	}

	public static List<MtInfoDiscoveryRequestListResponse> fromCollection(List<InfoDiscoveryRequest> infoDiscoveryRequests) {
		List<MtInfoDiscoveryRequestListResponse> lst = new ArrayList<>();
		for (InfoDiscoveryRequest infoDiscoveryRequest : infoDiscoveryRequests) {
			lst.add(from(infoDiscoveryRequest));
		}

		return lst;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Object createdDate) {
		if (createdDate instanceof String) {
			this.createdDate = (String) createdDate;
		} else if (createdDate instanceof Date) {
			this.createdDate = DateUtils.formatDate((Date) createdDate);
		}
	}
}
