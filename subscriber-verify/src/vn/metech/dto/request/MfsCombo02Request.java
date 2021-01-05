package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.entity.ConfirmInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfsCombo02Request extends MfsCombo01Request {

	@JsonProperty("imei")
	private String imeiNumber;

	@JsonProperty("dateCheckImei")
	private String imeiCheckDate;

	public MfsCombo02Request(ConfirmInfo confirmInfo, String account, String hashKey) {
		super(confirmInfo, account, hashKey);
		this.imeiCheckDate = getCheckDate();
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public String getImeiCheckDate() {
		return imeiCheckDate;
	}

	public void setImeiCheckDate(String imeiCheckDate) {
		this.imeiCheckDate = imeiCheckDate;
	}
}
