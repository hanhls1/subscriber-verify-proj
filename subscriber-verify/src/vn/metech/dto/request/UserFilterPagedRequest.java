package vn.metech.dto.request;

import vn.metech.shared.PagedRequest;

public class UserFilterPagedRequest extends PagedRequest {

	private String agentId;
	private String keyword;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
