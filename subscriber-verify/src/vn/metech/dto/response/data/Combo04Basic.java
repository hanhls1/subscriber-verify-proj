package vn.metech.dto.response.data;

import java.util.ArrayList;
import java.util.List;

public class Combo04Basic {

	private List<CallBase> referenceReports;
	private Integer imeiReport;

	public Combo04Basic() {
		this.referenceReports = new ArrayList<>();
	}

	public Combo04Basic(List<CallBase> referenceReports, Integer imeiReport) {
		this.referenceReports = referenceReports;
		this.imeiReport = imeiReport;
	}

	public List<CallBase> getReferenceReports() {
		return referenceReports;
	}

	public void setReferenceReports(List<CallBase> referenceReports) {
		this.referenceReports = referenceReports;
	}

	public Integer getImeiReport() {
		return imeiReport;
	}

	public void setImeiReport(Integer imeiReport) {
		this.imeiReport = imeiReport;
	}
}
