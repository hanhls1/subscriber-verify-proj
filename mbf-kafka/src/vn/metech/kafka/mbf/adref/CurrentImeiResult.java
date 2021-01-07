package vn.metech.kafka.mbf.adref;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CurrentImeiResult implements Serializable {

	@JsonProperty("phonenumber")
	private String phoneNumber;

	@JsonProperty("dateCheckImei")
	private Date dateToCheck;

	@JsonProperty("resultImei1")
	private AdResult result;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public Date getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(Date dateToCheck) {
		this.dateToCheck = dateToCheck;
	}

	public AdResult getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = AdResult.valueOf(result);
	}
}
