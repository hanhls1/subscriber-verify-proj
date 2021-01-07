package vn.metech.dto.request;

import vn.metech.util.StringUtils;

public class MtResponseRequest {

	private String requestId;
	private String questionRequestId;

	public MtResponseRequest() {
		this.requestId = StringUtils._3tId();
	}

	public MtResponseRequest(String questionRequestId) {
		this();
		this.questionRequestId = questionRequestId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getQuestionRequestId() {
		return questionRequestId;
	}

	public void setQuestionRequestId(String questionRequestId) {
		this.questionRequestId = questionRequestId;
	}
}
