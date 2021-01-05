package vn.metech.dto.request.monitor;

import vn.metech.dto.request.PageRequest;

import java.util.Date;

public class FilterRequest extends PageRequest {

    private String subPartnerId;

    private Date fromDate;
    private Date toDate;

    public FilterRequest() {
        this.toDate = new Date();
        this.fromDate = new Date();
    }

    public String getSubPartnerId() {
        return subPartnerId;
    }

    public void setSubPartnerId(String subPartnerId) {
        this.subPartnerId = subPartnerId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }



}
