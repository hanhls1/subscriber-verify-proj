package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Param;
import vn.metech.entity.ConfirmInfo;
import vn.metech.util.DateUtils;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfsImeiRequest extends MfsRequestBase {

	public MfsImeiRequest(ConfirmInfo confirmInfo, String account, String hashKey) {
		super(account, confirmInfo.getCustomerCode(), confirmInfo.getPhoneNumber(), hashKey);
		this.checkDate = confirmInfo.getParamValue(Param.CHECK_DATE);
		if (this.checkDate == null) {
			this.checkDate = DateUtils.formatDate(new Date());
		}
	}

	@JsonProperty("dateCheckImei")
	private String checkDate;

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}
