package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.common.Result;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Combo02Advance extends Combo01Advance {

	private Integer imeiReport;

	public Combo02Advance(LocationAdvance locationAdvance, CallReference callReference, Integer imeiReport) {
		super(locationAdvance, callReference);
		this.imeiReport = imeiReport;
	}

	public Integer getImeiReport() {
		return imeiReport;
	}

	public void setImeiReport(Integer imeiReport) {
		this.imeiReport = imeiReport;
	}

	public void setImeiReport(Result imeiReport) {
		this.imeiReport = imeiReport.value();
	}
}
