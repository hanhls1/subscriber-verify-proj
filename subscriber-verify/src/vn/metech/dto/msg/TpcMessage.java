package vn.metech.dto.msg;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.constant.AppService;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.RequestType;
import vn.metech.dto.*;
import vn.metech.entity.AdRequest;
import vn.metech.entity.AdResponse;
import vn.metech.entity.CallRequest;
import vn.metech.entity.CallResponse;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TpcMessage implements Serializable {

	private AppService appService;
	private String phoneNumber;
	private String requestId;
	private String createdBy;
	private Date createdDate;
	private Boolean scorePass;
	private Result imeiResult;
	private Result refPhone1Result;
	private Result refPhone2Result;
	private String scoreResult;
	private RequestStatus requestStatus;
	private String refPhone1;
	private String refPhone2;
	private String idNumber;
	private Boolean timeout;
	private String subscriberStatus;

	public TpcMessage() {
		this.scorePass = null;
		this.appService = AppService.AD_REFERENCE;
	}

	public TpcMessage(AdRequest adRequest) {
		this();
		this.requestId = adRequest.getId();
		this.createdBy = adRequest.getCreatedBy();
		this.phoneNumber = adRequest.getPhoneNumber();
		this.createdDate = adRequest.getCreatedDate();
		this.requestStatus = adRequest.getRequestStatus();
		this.timeout = adRequest.getRequestStatus() == RequestStatus.TIMEOUT;
		this.subscriberStatus = adRequest.getSubscriberStatus() == null ? null : adRequest.getSubscriberStatus().name();
		if (adRequest.getRequestStatus() == RequestStatus.ANSWER_RECEIVED
						|| adRequest.getRequestStatus() == RequestStatus.TIMEOUT
						|| adRequest.getRequestStatus() == RequestStatus.ANSWER_SENT) {
			if (adRequest.getAdResponse() != null) {
				AdResponse adResponse = adRequest.getAdResponse();
				if (!StringUtils.isEmpty(adResponse.getResponseData())) {
					MbfAdReferenceResult adResult = JsonUtils.toObject(adResponse.getResponseData(), MbfAdReferenceResult.class);
					if (adResult != null) {
						if (adResult.getCurrentImeiResult() != null) {
							this.imeiResult = adResult.getCurrentImeiResult().getResult();
						}
					}
				}
			}
		}
	}

	public TpcMessage(CallRequest callRequest) {
		this();
		this.requestId = callRequest.getId();
		this.createdBy = callRequest.getCreatedBy();
		this.phoneNumber = callRequest.getPhoneNumber();
		this.createdDate = callRequest.getCreatedDate();
		this.requestStatus = callRequest.getRequestStatus();
		this.refPhone1 = callRequest.getRefPhone1();
		this.refPhone2 = callRequest.getRefPhone2();
		this.timeout = callRequest.getRequestStatus() == RequestStatus.TIMEOUT;
		this.subscriberStatus = callRequest.getSubscriberStatus() == null ? null : callRequest.getSubscriberStatus().name();
		if (callRequest.getRequestStatus() == RequestStatus.ANSWER_RECEIVED
				|| callRequest.getRequestStatus() == RequestStatus.TIMEOUT
				|| callRequest.getRequestStatus() == RequestStatus.ANSWER_SENT) {
			if (callRequest.getCallResponse() != null) {
				CallResponse callResponse = callRequest.getCallResponse();
				if (!StringUtils.isEmpty(callResponse.getResponseData())) {
					CallStatus callStatus = null;
					if (callResponse.getRequestType() == RequestType.BASIC) {
						CallBasicResult callBasicResult =
								JsonUtils.toObject(callResponse.getResponseData(), CallBasicResult.class);
						callStatus = callBasicResult.getCallStatus();
					} else {
						MbfCallReferenceResult mbfCallReferenceResult =
								JsonUtils.toObject(callResponse.getResponseData(), MbfCallReferenceResult.class);
						callStatus = mbfCallReferenceResult.getCallStatus();
					}
					if (callStatus != null) {
						this.refPhone1Result = callStatus.getStatus1();
						this.refPhone2Result = callStatus.getStatus2();
					}
				}
			}
		}

	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getScorePass() {
		return scorePass;
	}

	public void setScorePass(Boolean scorePass) {
		this.scorePass = scorePass;
	}

	public Result getImeiResult() {
		return imeiResult;
	}

	public void setImeiResult(Result imeiResult) {
		this.imeiResult = imeiResult;
	}

	public Result getRefPhone1Result() {
		return refPhone1Result;
	}

	public void setRefPhone1Result(Result refPhone1Result) {
		this.refPhone1Result = refPhone1Result;
	}

	public Result getRefPhone2Result() {
		return refPhone2Result;
	}

	public void setRefPhone2Result(Result refPhone2Result) {
		this.refPhone2Result = refPhone2Result;
	}

	public String getScoreResult() {
		return scoreResult;
	}

	public void setScoreResult(String scoreResult) {
		this.scoreResult = scoreResult;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRefPhone1() {
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Boolean getTimeout() {
		return timeout;
	}

	public void setTimeout(Boolean timeout) {
		this.timeout = timeout;
	}

	public String getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}
}
