package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import vn.metech.dto.PastIdNumber;
import vn.metech.dto.Result;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MtPastIdNumberResponse implements Serializable {

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("idNumber")
	private String idNumber;

	@JsonProperty("result")
	private Result result;

	@JsonProperty("dateToCheck")
	private String dateToCheck;

	public MtPastIdNumberResponse(
					String phoneNumber, String idNumber, String dateToCheck) {
		this.phoneNumber = phoneNumber;
		this.idNumber = idNumber;
		this.dateToCheck = dateToCheck;
	}

	public MtPastIdNumberResponse(PastIdNumber pastIdNumber) {
		Assert.notNull(pastIdNumber, "[pastIdNumber] is null");

		this.phoneNumber = pastIdNumber.getPhoneNumber();
		this.idNumber = pastIdNumber.getIdNumber();
		this.result = pastIdNumber.getResult();
		this.dateToCheck = pastIdNumber.getDateToCheck() == null
						? null
						: DateUtils.formatDate(pastIdNumber.getDateToCheck());
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(String dateToCheck) {
		this.dateToCheck = dateToCheck;
	}
}
