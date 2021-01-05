package vn.metech.dto.response;

import vn.metech.entity.UserMapping;

public class RefUserResponse {

	private String mappingId;
	private String refUserId;
	private String refEmail;

	public RefUserResponse(UserMapping userMapping) {
		this.refUserId = userMapping.getRefUserId();
		this.refEmail = userMapping.getEmail();
		this.mappingId = userMapping.getId();
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getRefUserId() {
		return refUserId;
	}

	public void setRefUserId(String refUserId) {
		this.refUserId = refUserId;
	}

	public String getRefEmail() {
		return refEmail;
	}

	public void setRefEmail(String refEmail) {
		this.refEmail = refEmail;
	}
}
