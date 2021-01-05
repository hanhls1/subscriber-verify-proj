package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MfsSuccessRequestList {

	@JsonProperty("listRequestId")
	private List<MfsSuccessRequest> successRequests;

	public MfsSuccessRequestList() {
		this.successRequests = new ArrayList<>();
	}

	public List<String> requestIds() {
		if (successRequests.size() == 0) {
			return Collections.emptyList();
		}

		return successRequests.stream().map(MfsSuccessRequest::getRequestId).collect(Collectors.toList());
	}

	public List<MfsSuccessRequest> getSuccessRequests() {
		return successRequests;
	}

	public void setSuccessRequests(List<MfsSuccessRequest> successRequests) {
		this.successRequests = successRequests;
	}
}
