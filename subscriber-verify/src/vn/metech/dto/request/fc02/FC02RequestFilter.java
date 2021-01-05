package vn.metech.dto.request.fc02;


import vn.metech.dto.request.PageRequest;

import java.util.Date;

public class FC02RequestFilter extends PageRequest {

    private String phoneNumber;
    private Date fromDate;
    private Date toDate;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getFromDate() {
        return fromDate;
    }

//    public void setFromDate(Object fromDate) {
//        if (fromDate instanceof Date) {
//            this.fromDate = (Date) fromDate;
//        } else if (fromDate instanceof String) {
//            this.fromDate = DateUtils.parseDate((String) fromDate);
//        }
//    }


    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    //    public void setToDate(Object toDate) {
//        if (toDate instanceof Date) {
//            this.toDate = (Date) toDate;
//        } else if (toDate instanceof String) {
//            this.toDate = DateUtils.parseDate((String) toDate);
//        }
//    }
}
