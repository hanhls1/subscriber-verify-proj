package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.RequestStatus;
import vn.metech.entity.IdRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MtIdRequestListResponse implements Serializable {

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

	private MbfStatus partnerStatus;

	public static MtIdRequestListResponse from(IdRequest idRequest) {
		MtIdRequestListResponse response = new MtIdRequestListResponse();
		response.requestId = idRequest.getId();
		response.phoneNumber = idRequest.getPhoneNumber();
		response.idNumber = idRequest.getIdNumber();
		response.requestStatus = idRequest.getRequestStatus();
		response.createdDate = DateUtils.formatDate(idRequest.getCreatedDate());
		response.partnerStatus = idRequest.getMbfStatus();

		return response;
	}

	public static List<MtIdRequestListResponse> fromList(List<IdRequest> idRequests) {
		List<MtIdRequestListResponse> lst = new ArrayList<>();
		for (IdRequest idRequest : idRequests) {
			lst.add(from(idRequest));
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

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public MbfStatus getPartnerStatus() {
		return partnerStatus;
	}

	public void setPartnerStatus(MbfStatus partnerStatus) {
		this.partnerStatus = partnerStatus;
	}
}
