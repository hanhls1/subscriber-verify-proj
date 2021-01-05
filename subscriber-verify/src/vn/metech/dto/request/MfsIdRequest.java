package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import vn.metech.common.Param;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.ConfirmParam;

import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfsIdRequest extends MfsRequestBase {

	@JsonProperty("id")
	private String idNumber;

	@JsonProperty("dateCheckId")
	private String checkDate;

	public MfsIdRequest(ConfirmInfo confirmInfo, String account, String hashKey) {
		super(account, confirmInfo.getCustomerCode(), confirmInfo.getPhoneNumber(), hashKey);
		Assert.notNull(confirmInfo.getParams(), "params required");
		Map<Param, ConfirmParam> params = confirmInfo.getParams();
		this.idNumber = confirmInfo.getParamValue(Param.ID_NUMBER);
		ConfirmParam checkDate = params.get(Param.CHECK_DATE);
		if (checkDate != null && StringUtils.isEmpty(checkDate.getValue())) {
			this.checkDate = checkDate.getValue();
		}
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}
