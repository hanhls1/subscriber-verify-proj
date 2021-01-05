package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.util.DateUtils;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static vn.metech.util.DateUtils.DATE_FORMAT;

@JsonInclude(NON_EMPTY)
public class MbfLocation extends BaseRequest {

	@JsonProperty("datetime")
	private String checkDate;

	@JsonProperty("totaldate")
	private Integer totalDate;

	@JsonProperty("homeaddress")
	private MbfAddress homeAddress;

	@JsonProperty("workaddress")
	private MbfAddress workAddress;

	@JsonProperty("referaddress")
	private MbfAddress referAddress;

	private MbfLocation() {
		this.checkDate = DateUtils.formatDate(new Date(), DATE_FORMAT);
	}

	public MbfLocation(String customerCode, String phoneNumber) {
		super(customerCode, phoneNumber);
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getTotalDate() {
		return totalDate;
	}

	public void setTotalDate(Integer totalDate) {
		this.totalDate = totalDate;
	}

	public Object getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(MbfAddress homeAddress) {
		this.homeAddress = homeAddress;
	}

	public MbfAddress getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(MbfAddress workAddress) {
		this.workAddress = workAddress;
	}

	public MbfAddress getReferAddress() {
		return referAddress;
	}

	public void setReferAddress(MbfAddress referAddress) {
		this.referAddress = referAddress;
	}
}
