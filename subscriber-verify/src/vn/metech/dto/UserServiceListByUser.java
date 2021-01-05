package vn.metech.dto;

import java.util.ArrayList;
import java.util.List;

public class UserServiceListByUser {

	private String userId;
	private List<ServiceTypeDto> services;

	public UserServiceListByUser() {
		this.services = new ArrayList<>();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ServiceTypeDto> getServices() {
		return services;
	}

	public void setServices(List<ServiceTypeDto> services) {
		this.services = services;
	}
}
