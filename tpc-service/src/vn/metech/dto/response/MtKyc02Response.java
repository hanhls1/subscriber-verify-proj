package vn.metech.dto.response;

import org.springframework.util.StringUtils;
import vn.metech.constant.DwhStatus;
import vn.metech.dto.DwhKycResult;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.util.JsonUtils;

import java.io.Serializable;

public class MtKyc02Response implements Serializable {

	private String requestId;
	private String responseId;
	private DwhStatus status;
	private String description;
	private DwhKycResult data;

	public MtKyc02Response() {
		this.status = DwhStatus.UNDEFINED;
		this.description = DwhStatus.UNDEFINED.message();
	}

	public MtKyc02Response(String partnerRequestId, InfoDiscoveryResponse infoDiscoveryResponse) {
		this();
		this.requestId = partnerRequestId;
		this.responseId = infoDiscoveryResponse.getId();
		if (infoDiscoveryResponse.getDwhStatus() != null) {
			this.status = infoDiscoveryResponse.getDwhStatus();
			this.description = infoDiscoveryResponse.getDwhStatus().message();
		}
		if (!StringUtils.isEmpty(infoDiscoveryResponse.getResponseData())) {
			this.data = JsonUtils.toObject(infoDiscoveryResponse.getResponseData(), DwhKycResult.class);
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

	public DwhStatus getStatus() {
		return status;
	}

	public void setStatus(DwhStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DwhKycResult getData() {
		return data;
	}

	public void setData(DwhKycResult data) {
		this.data = data;
	}
}
