package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.ConfirmInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AdvanceReport<T> extends HashResponseBase {

    private String phoneNumber;
    private String reportDate;
    private T reports;

    public AdvanceReport() {
    }

    public AdvanceReport(ConfirmInfo confirmInfo) {
        this();
        this.phoneNumber = confirmInfo.getPhoneNumber();
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

    public T getReports() {
        return reports;
    }

    public void setReports(T reports) {
        this.reports = reports;
    }
}
