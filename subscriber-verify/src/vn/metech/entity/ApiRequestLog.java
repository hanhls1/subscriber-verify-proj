package vn.metech.entity;


import vn.metech.entity.base.EntityBase;
import vn.metech.util.JsonUtils;
import vn.metech.util.RestUtils.RestResponse;
import vn.metech.util.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "api_request_log")
public class ApiRequestLog extends EntityBase {

	@Column(name = "request_url", columnDefinition = "nvarchar(2000)")
	private String requestUrl;

	@Column(name = "request_id", length = 36)
	private String requestId;

	@Column(name = "response_id", length = 36)
	private String responseId;

	@Column(name = "secure_hash", length = 64)
	private String secureHash;

	@Column(name = "request_body", columnDefinition = "nvarchar(4000)")
	private String requestBody;

	@Column(name = "response_body", columnDefinition = "nvarchar(4000)")
	private String responseBody;

	@Column(name = "response_body_1", columnDefinition = "nvarchar(4000)")
	private String responseBody1;

	@Column(name = "take_time")
	private Long takeTime;

	@Column(name = "message", columnDefinition = "nvarchar(1000)")
	private String message;

	@JoinColumn(name = "confirm_info_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private ConfirmInfo confirmInfo;

	public ApiRequestLog() {
	}

	public ApiRequestLog(RestResponse<?> res) {
		this();
		this.requestUrl = res.getRequestUrl();
		this.message = res.getResponseMsg();
		this.takeTime = res.getTakeTime();
		this.requestBody = JsonUtils.toJson(res.getRequestBody());
		this.setResponseBody(JsonUtils.toJson(res.getBody()));
	}

	public ApiRequestLog(RestResponse<?> res, ConfirmInfo confirmInfo) {
		this(res);
		this.secureHash = confirmInfo.getSecureHash();
		this.requestId = confirmInfo.getRequestId();
		this.confirmInfo = confirmInfo;
	}

	public ApiRequestLog(RestResponse<?> res, String requestId) {
		this(res);
		this.requestId = requestId;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
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

	public String getSecureHash() {
		return secureHash;
	}

	public void setSecureHash(String secureHash) {
		this.secureHash = secureHash;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		int jsonLength = responseBody == null ? 0 : responseBody.length();
		if (jsonLength > 4000) {
			this.responseBody = responseBody.substring(0, 3900);
			this.responseBody1 = responseBody.substring(3900, Math.min(jsonLength - 3900, 3900));
		} else {
			this.responseBody = responseBody;
		}
	}

	public String getResponseBody1() {
		return responseBody1;
	}

	public void setResponseBody1(String responseBody1) {
		this.responseBody1 = responseBody1;
	}

	public Long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Long takeTime) {
		this.takeTime = takeTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ConfirmInfo getConfirmInfo() {
		return confirmInfo;
	}

	public void setConfirmInfo(ConfirmInfo confirmInfo) {
		this.confirmInfo = confirmInfo;
	}

	public String responseBody() {
		String responseBody1 = StringUtils.isEmpty(this.responseBody1) ? "" : this.responseBody1;
		return responseBody + responseBody1;
	}
}
