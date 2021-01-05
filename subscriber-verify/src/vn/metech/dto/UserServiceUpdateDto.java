package vn.metech.dto;

import java.util.ArrayList;
import java.util.List;

public class UserServiceUpdateDto {

	private String userId;
	private List<String> serviceCodes;

	public UserServiceUpdateDto() {
		this.serviceCodes = new ArrayList<>();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getServiceCodes() {
		return serviceCodes;
	}

	public void setServiceCodes(List<String> serviceCodes) {
		this.serviceCodes = serviceCodes;
	}
}
