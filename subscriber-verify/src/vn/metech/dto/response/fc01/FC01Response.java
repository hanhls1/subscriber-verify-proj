package vn.metech.dto.response.fc01;

import vn.metech.dto.response.AdvanceReport;
import vn.metech.dto.response.data.IdReference;

public class FC01Response {

    public FC01Response() {

    }

    public FC01Response(AdvanceReport<IdReference> advanceReport) {
        this.phoneNumber = advanceReport.getPhoneNumber();
        IdReference idReference = advanceReport.getReports();
        this.idNumber = idReference.getIdNumber();
        this.reportDate = idReference.getReportDate();
        this.status = idReference.getResult();
    }

    private String idNumber;
    private String phoneNumber;
    private String reportDate;
    private Integer status;

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
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
