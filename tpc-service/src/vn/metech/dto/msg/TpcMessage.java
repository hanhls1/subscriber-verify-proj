package vn.metech.dto.msg;

import vn.metech.constant.AppService;
import vn.metech.constant.RequestStatus;
import vn.metech.kafka.mbf.adref.AdResult;
import vn.metech.kafka.mbf.callref.CallResult;
import vn.metech.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class TpcMessage implements Serializable {

	@NotNull
	private AppService appService;
	private String phoneNumber;
	private String requestId;
	private String createdBy;
	private Date createdDate;
	private Boolean scorePass;
	private AdResult imeiResult;
	private CallResult refPhone1Result;
	private CallResult refPhone2Result;
	private String scoreResult;

	@NotNull
	private RequestStatus requestStatus;
	private String refPhone1;
	private String refPhone2;
	private String idNumber;
	private Boolean timeout;
	private String subscriberStatus;

	public TpcMessage() {
		this.scorePass = null;
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

	public AdResult getImeiResult() {
		return imeiResult;
	}

	public void setImeiResult(AdResult imeiResult) {
		this.imeiResult = imeiResult;
	}

	public CallResult getRefPhone1Result() {
		return refPhone1Result;
	}

	public void setRefPhone1Result(CallResult refPhone1Result) {
		this.refPhone1Result = refPhone1Result;
	}

	public CallResult getRefPhone2Result() {
		return refPhone2Result;
	}

	public void setRefPhone2Result(CallResult refPhone2Result) {
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
