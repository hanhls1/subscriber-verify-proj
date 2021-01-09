package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.metech.common.ServiceType;
import vn.metech.entity.ConfirmInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonitorResponse {

    private String requestId;
    private String phoneNumber;

    @JsonFormat(pattern="yyyy/MM/dd - HH:mm:ss")
    private Date createdDate;
    private int statusCode;
    private String status;
    private ServiceType serviceType;
    private String account;
    private String partnerName;
    private String subPartnerName;
    private String telco="MBF";
    private String partnerId;

    public static MonitorResponse of(ConfirmInfo confirmInfo) {
        MonitorResponse monitorResponse = new MonitorResponse();
        monitorResponse.requestId = confirmInfo.getRequestId();
        monitorResponse.status = confirmInfo.getStatus();
        monitorResponse.statusCode = confirmInfo.getStatusCode();
        monitorResponse.phoneNumber = confirmInfo.getPhoneNumber();
        monitorResponse.createdDate = confirmInfo.getCreatedDate();
        monitorResponse.account = confirmInfo.getAccount();
        if (confirmInfo.getPartnerId() != null) {
            monitorResponse.partnerId = confirmInfo.getPartnerId();
            monitorResponse.partnerName = confirmInfo.getPartnerName();
        }

        monitorResponse.serviceType = confirmInfo.getServiceType();
        monitorResponse.setSubPartnerName(confirmInfo.getSubPartnerName());
        return monitorResponse;
    }

    public static List<MonitorResponse> of(List<ConfirmInfo> confirmInfo) {
        List<MonitorResponse> monitorResponses = new ArrayList<>();
        confirmInfo.forEach(c -> monitorResponses.add(MonitorResponse.of(c)));

        return monitorResponses;
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

    public String getSubPartnerName() {
        return subPartnerName;
    }

    public void setSubPartnerName(String subPartnerName) {
        this.subPartnerName = subPartnerName;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }
}
