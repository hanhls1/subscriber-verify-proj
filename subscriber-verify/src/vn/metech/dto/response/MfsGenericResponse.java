package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsGenericResponse<T> extends MfsResponseBase {

	@JsonProperty("data")
	private T responseData;

	public T getResponseData() {
		return responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}
}
