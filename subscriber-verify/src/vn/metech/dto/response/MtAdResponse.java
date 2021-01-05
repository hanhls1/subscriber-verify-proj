package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.SubscriberStatus;
import vn.metech.constant.VerifyService;
import vn.metech.dto.MbfAdReferenceResult;
import vn.metech.dto.Result;
import vn.metech.entity.AdResponse;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;

public class MtAdResponse implements Serializable {

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("responseId")
	private String responseId;

	@JsonProperty("dateToCheck")
	private String dateToCheck;

	@JsonProperty("status1")
	private Result status1;

	@JsonProperty("status2")
	private Result status2;

	@JsonProperty("createdDate")
	private String createdDate;

	private SubscriberStatus subscriberStatus;

	private MbfStatus partnerStatus;

	public MtAdResponse() {
	}

	public MtAdResponse(AdResponse adResponse) {
		this();
		this.responseId = adResponse.getId();
		this.phoneNumber = adResponse.getPhoneNumber();
		this.dateToCheck = DateUtils.formatDate(adResponse.getDateToCheck());
		this.createdDate = DateUtils.formatDate(adResponse.getCreatedDate());
		this.partnerStatus = adResponse.getMbfStatus();
		this.subscriberStatus = adResponse.getSubscriberStatus();
		if (!StringUtils.isEmpty(adResponse.getResponseData())) {
			MbfAdReferenceResult mbfAdReferenceResult =
							JsonUtils.toObject(adResponse.getResponseData(), MbfAdReferenceResult.class);
			if (mbfAdReferenceResult != null) {
				if (mbfAdReferenceResult.getCurrentImeiResult() != null) {
					this.status1 = mbfAdReferenceResult.getCurrentImeiResult().getResult();
				}
				if (mbfAdReferenceResult.getLatestImeiResult() != null) {
					if (adResponse.getVerifyService() == VerifyService.FC02_02) {
						this.status2 = mbfAdReferenceResult.getLatestImeiResult().getResult();
					}
				}
			}
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

	public SubscriberStatus getSubscriberStatus() {
		return subscriberStatus != null ? subscriberStatus : SubscriberStatus.NO_STATUS;
	}

	public void setSubscriberStatus(SubscriberStatus subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

	public void setPartnerStatus(MbfStatus partnerStatus) {
		this.partnerStatus = partnerStatus;
	}
}
