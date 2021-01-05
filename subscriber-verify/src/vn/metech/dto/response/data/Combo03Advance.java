package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Combo03Advance extends Combo02Advance {

	private IdReport idReports;

	public Combo03Advance(
					LocationAdvance locationAdvance, CallReference callReference, Integer imeiReport, IdReport idReports) {
		super(locationAdvance, callReference, imeiReport);
		this.idReports = idReports;
	}

	public Combo03Advance(LocationAdvance locationAdvance,
	                      CallReference callReference, Integer imeiReport, String idNumber, Integer res) {
		this(locationAdvance, callReference, imeiReport, null);
		this.idReports = new IdReport(idNumber, res);
	}

	public IdReport getIdReports() {
		return idReports;
	}

	public void setIdReports(IdReport idReports) {
		this.idReports = idReports;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public class IdReport {

		private String idNumber;
		private Integer status;

		public IdReport() {
		}

		public IdReport(String idNumber, Integer status) {
			this.idNumber = idNumber;
			this.status = status;
		}

		public String getIdNumber() {
			return idNumber;
		}

		public void setIdNumber(String idNumber) {
			this.idNumber = idNumber;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}
	}
}
