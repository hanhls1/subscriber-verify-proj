package vn.metech.dto.response;

import vn.metech.dto.ServiceTypeDto;
import vn.metech.dto.UserServiceDto;

import java.util.ArrayList;
import java.util.List;

public class UserServiceResponse {

	private List<ServiceTypeDto> serviceTypes;
	private List<UserServiceDto> userServices;

	public UserServiceResponse() {
		this.serviceTypes = new ArrayList<>();
		this.userServices = new ArrayList<>();
	}

	public List<ServiceTypeDto> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(List<ServiceTypeDto> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	public List<UserServiceDto> getUserServices() {
		return userServices;
	}

	public void setUserServices(List<UserServiceDto> userServices) {
		this.userServices = userServices;
	}
}
