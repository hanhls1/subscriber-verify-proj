package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.MbfStatus;
import vn.metech.dto.MbfIdReferenceResult;
import vn.metech.dto.Result;
import vn.metech.entity.IdResponse;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;

public class MtIdResponse implements Serializable {

	@JsonProperty("idNumber")
	private String idNumber;

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("responseId")
	private String responseId;

	@JsonProperty("status1")
	private Result status1;

	@JsonProperty("status2")
	private Result status2;

	@JsonProperty("dateToCheck")
	private String dateToCheck;

	@JsonProperty("createdDate")
	private String createdDate;

	private MbfStatus partnerStatus;

	public MtIdResponse() {
	}

	public MtIdResponse(IdResponse idResponse) {
		this.responseId = idResponse.getId();
		this.idNumber = idResponse.getIdNumber();
		this.phoneNumber = idResponse.getPhoneNumber();
		this.partnerStatus = idResponse.getMbfStatus();
		this.dateToCheck = DateUtils.formatDate(idResponse.getDateToCheck());
		this.createdDate = DateUtils.formatDate(idResponse.getCreatedDate());
		if (!StringUtils.isEmpty(idResponse.getResponseData())) {
			MbfIdReferenceResult idResult = JsonUtils.toObject(idResponse.getResponseData(), MbfIdReferenceResult.class);
			if (idResult != null) {
				if (idResult.getCurrentIdNumber() != null) {
					this.status2 = idResult.getCurrentIdNumber().getResult();
				}
				if (idResult.getPastIdNumber() != null) {
					this.status1 = idResult.getPastIdNumber().getResult();
				}
			}
		}
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Result getStatus1() {
		return status1;
	}

	public void setStatus1(Result status1) {
		this.status1 = status1;
	}

	public Result getStatus2() {
		return status2;
	}

	public void setStatus2(Result status2) {
		this.status2 = status2;
	}

	public String getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(String dateToCheck) {
		this.dateToCheck = dateToCheck;
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
