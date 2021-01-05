package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SimpleReport<T> extends HashResponseBase {

	private String phoneNumber;
	private T report;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public T getReport() {
		return report;
	}

	public void setReport(T report) {
		this.report = report;
	}
}
