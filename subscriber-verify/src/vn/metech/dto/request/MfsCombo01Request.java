package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Param;
import vn.metech.entity.ConfirmInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfsCombo01Request extends MfsLocationRequest {

	@JsonProperty("refPhone1")
	private String refPhone1;

	@JsonProperty("refPhone2")
	private String refPhone2;

	public MfsCombo01Request(ConfirmInfo confirmInfo, String account, String hashKey) {
		super(confirmInfo, account, hashKey);
		this.refPhone1 = confirmInfo.getParamValue(Param.REF_PHONE_1);
		this.refPhone2 = confirmInfo.getParamValue(Param.REF_PHONE_2);
	}

	public String getRefPhone1() {
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
	}

}
