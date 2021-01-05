package vn.metech.dto.request.monitor;
;

import vn.metech.common.ServiceType;
import vn.metech.dto.request.PageRequest;
import vn.metech.util.StringUtils;

import java.util.Arrays;
import java.util.Date;

public class ConfirmRequest extends PageRequest {

    private String status;
    private String phoneNumber;
    private ServiceType serviceType;
    private String partnerId;
    private String subPartnerId;
    private String isReport;

    private Date fromDate;
    private Date toDate;

    public ConfirmRequest() {
        this.phoneNumber = "";
        this.toDate = new Date();
        this.fromDate = new Date();
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
    public String getIsReport() {
        return isReport;
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        if (!StringUtils.isEmpty(this.phoneNumber)) {
            this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(Object serviceType) {
        if (serviceType == null) return;

        if (serviceType instanceof String) {
            this.serviceType = ServiceType.fromName((String)serviceType);
        } else if (serviceType instanceof ServiceType) {
            this.serviceType = (ServiceType) serviceType;
        }
    }


    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSubPartnerId() {
        return subPartnerId;
    }

    public void setSubPartnerId(String subPartnerId) {
        this.subPartnerId = subPartnerId;
    }
}
