package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MfsSuccessResponse {

	@JsonProperty("listRequestId")
	private List<MfsQRequestId> requestIds;

	public MfsSuccessResponse() {
		this.requestIds = new ArrayList<>();
	}

	public List<String> requestIds() {
		if (requestIds.size() == 0) {
			return Collections.emptyList();
		}

		return this.requestIds.stream().map(MfsQRequestId::getqRequestId).collect(Collectors.toList());
	}

	public List<MfsQRequestId> getRequestIds() {
		return requestIds;
	}

	public void setRequestIds(List<MfsQRequestId> requestIds) {
		this.requestIds = requestIds;
	}
}
