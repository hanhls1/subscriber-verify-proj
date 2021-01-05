package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Param;
import vn.metech.dto.MfsLocation;
import vn.metech.entity.ConfirmInfo;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.util.Date;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class MfsLocationRequest extends MfsRequestBase {

	public MfsLocationRequest(ConfirmInfo confirmInfo, String account, String hashKey) {
		super(account, confirmInfo.getCustomerCode(), confirmInfo.getPhoneNumber(), hashKey);
		this.totalDate = 90;
		this.refAddr = new MfsLocation(0.0, 0.0, 0);
		this.checkDate = confirmInfo.getParamValue(Param.CHECK_DATE);
		if (StringUtils.isEmpty(checkDate)) {
			this.checkDate = DateUtils.formatDate(new Date());
		}
	}

	@JsonProperty("datetime")
	private String checkDate;

	@JsonProperty("totaldate")
	private int totalDate;

	@JsonProperty("homeaddress")
	private MfsLocation homeAddr;

	@JsonProperty("workaddress")
	private MfsLocation workAddr;

	@JsonProperty("referaddress")
	private MfsLocation refAddr;

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public int getTotalDate() {
		return totalDate;
	}

	public void setTotalDate(int totalDate) {
		this.totalDate = totalDate;
	}

	public MfsLocation getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(MfsLocation homeAddr) {
		this.homeAddr = homeAddr;
	}

	public MfsLocation getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(MfsLocation workAddr) {
		this.workAddr = workAddr;
	}

	public MfsLocation getRefAddr() {
		return refAddr;
	}

	public void setRefAddr(MfsLocation refAddr) {
		this.refAddr = refAddr;
	}
}
