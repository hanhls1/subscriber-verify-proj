package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.constant.Regex;
import vn.metech.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MtInfoDiscoveryRequest implements Serializable {

	@NotNull
	private String requestId;

	@NotNull
	private String idNumber;

	private String phoneNumber;

	private String customerName;

	private String companyName;

	@Pattern(regexp = Regex.PRICE)
	private String incomingRange;

	@Pattern(regexp = Regex.NUMBER_WITH_2_DIGIT)
	private String numOfDependent;

	public MtInfoDiscoveryRequest() {
		this.requestId = StringUtils._3tId();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = this.phoneNumber.trim();
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIncomingRange() {
		return incomingRange;
	}

	public void setIncomingRange(String incomingRange) {
		this.incomingRange = incomingRange;
	}

	public String getNumOfDependent() {
		return numOfDependent;
	}

	public void setNumOfDependent(String numOfDependent) {
		this.numOfDependent = numOfDependent;
	}
}
