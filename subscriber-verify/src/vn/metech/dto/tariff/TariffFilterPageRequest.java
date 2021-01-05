package vn.metech.dto.tariff;

import vn.metech.shared.PagedRequest;

public class TariffFilterPageRequest extends PagedRequest {

	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
