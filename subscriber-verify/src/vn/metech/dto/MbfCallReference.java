package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class MbfCallReference extends BaseRequest {

	@JsonProperty("refPhone1")
	private String refPhone1;

	@JsonProperty("refPhone2")
	private String refPhone2;

	private MbfCallReference() {
		super();
	}

	public MbfCallReference(String customerCode, String phoneNumber) {
		super(customerCode, phoneNumber);
	}

	public String getRefPhone1() {
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
	}
}
