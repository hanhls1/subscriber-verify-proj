package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import vn.metech.dto.LatestImeiResult;
import vn.metech.dto.Result;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;

public class MtPastImeiResponse implements Serializable {

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("dateToCheck")
	private String dateToCheck;

	@JsonProperty("result")
	private Result result;

	@JsonProperty("description")
	private String description;

	public MtPastImeiResponse(LatestImeiResult result) {
		Assert.notNull(result, "[result] is null");

		this.phoneNumber = result.getPhoneNumber();
		this.result = result.getResult();
		this.description = result.getResult().message();
		this.dateToCheck = result.getDateToCheck() == null
						? null
						: DateUtils.formatDate(result.getDateToCheck());
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public String getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(String dateToCheck) {
		this.dateToCheck = dateToCheck;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
