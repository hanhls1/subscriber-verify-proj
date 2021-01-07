package vn.metech.kafka.mbf;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.constant.AppService;
import vn.metech.constant.RecordStatus;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PartnerStatusMsg implements Serializable {

	private AppService appService;
	private String requestId;
	private String partnerRequestId;
	private RecordStatus recordStatus;
	private String partnerStatus;
	private String subscriberStatus;

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getPartnerRequestId() {
		return partnerRequestId;
	}

	public void setPartnerRequestId(String partnerRequestId) {
		this.partnerRequestId = partnerRequestId;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getPartnerStatus() {
		return partnerStatus;
	}

	public void setPartnerStatus(String partnerStatus) {
		this.partnerStatus = partnerStatus;
	}

	public String getSubscriberStatus() {
		return subscriberStatus;
	}

	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}
}
