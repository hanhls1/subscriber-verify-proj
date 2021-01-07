package vn.metech.kafka.mbf.adref;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.kafka.mbf.BaseRequest;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class MbfAdReference extends BaseRequest {

	@JsonProperty("dateCheckImei")
	private String dateToCheck;

	private MbfAdReference() {
		super();
	}

	public MbfAdReference(String customerCode, String phoneNumber) {
		super(customerCode, phoneNumber);
	}

	public String getDateToCheck() {
		return dateToCheck;
	}

	public void setDateToCheck(String dateToCheck) {
		this.dateToCheck = dateToCheck;
	}
}
