package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.util.StringUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CurrentIdNumber implements Serializable {

	@JsonProperty("phonenumber")
	private String phoneNumber;

	@JsonProperty("id")
	private String idNumber;

	@JsonProperty("resultId2")
	private Result result;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Object result) {
		if (result instanceof String) {
			try {
				int val = Integer.parseInt((String) result);
				this.result = Result.valueOf(val);
			} catch (Exception ignored) {
				this.result = Result.valueOf((String) result);
			}
		} else if (result instanceof Result) {
			this.result = (Result) result;
		}
	}
}
