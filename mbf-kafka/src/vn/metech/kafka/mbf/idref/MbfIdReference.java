package vn.metech.kafka.mbf.idref;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.kafka.mbf.BaseRequest;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class MbfIdReference extends BaseRequest {

	@JsonProperty("id")
	private String idNumber;

	@JsonProperty("dateCheckId")
	private String dateToCheck;

	private MbfIdReference() {
		super();
	}

	public MbfIdReference(String customerCode, String phoneNumber) {
		super(customerCode, phoneNumber);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(String dateToCheck) {
		this.dateToCheck = dateToCheck;
	}
}
