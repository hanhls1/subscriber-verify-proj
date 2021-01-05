package vn.metech.dto;

public class ServiceTypeDto {

	private String code;
	private String displayName;

	public ServiceTypeDto(ServiceType serviceType) {
		this.code = serviceType.name();
		this.displayName = serviceType.displayName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
