package vn.metech.dto;

import java.util.ArrayList;
import java.util.List;

public class UserServiceDto {

	private List<String> userIds;
	private String serviceCode;

	public UserServiceDto() {
		this.userIds = new ArrayList<>();
	}

	public UserServiceDto(String serviceCode) {
		this();
		this.serviceCode = serviceCode;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
}
