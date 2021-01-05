package vn.metech.dto.request;

import vn.metech.shared.PagedRequest;

public class PartnerPageRequest extends PagedRequest {

	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
