package vn.metech.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserMappingUpdateRequest {

	@NotNull
	@NotEmpty
	private String mappingId;

	@NotNull
	@NotEmpty
	private String refUserId;

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
}
