package vn.metech.dto.monitor;


import vn.metech.common.ServiceType;

import java.util.Date;

public class ConfirmInfoDto extends PageDto{
    private String requestId;
    private String phoneNumber;
    private Date createdDate;
    private int statusCode;
    private String status;
    private ServiceType serviceType;
    private String account;
    private String partnerName;
    private String partnerId;


    public ConfirmInfoDto(String requestId, String phoneNumber, int statusCode, String status, ServiceType serviceType, Date createdDate, String account, String partnerName, String partnerId) {
        this.requestId = requestId;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.statusCode = statusCode;
        this.status = status;
        this.serviceType = serviceType;
        this.account = account;
        this.partnerName = partnerName;
        this.partnerId = partnerId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
