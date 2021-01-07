package vn.metech.entity;

import vn.metech.constant.*;
import vn.metech.dto.request.MtInfoDiscoveryRequest;
import vn.metech.shared.BaseEntity;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import static vn.metech.constant.RequestStatus.PENDING;

@Entity
@Table(name = "InfoDiscoveryRequest")
public class InfoDiscoveryRequest extends BaseEntity {

    @Enumerated(STRING)
    @Column(name = "AppService", length = 50)
    private AppService appService;

    @Enumerated(STRING)
    @Column(name = "VerifyService", length = 50)
    private VerifyService verifyService;

    @Enumerated(STRING)
    @Column(name = "RequestType", length = 50)
    private RequestType requestType;

    @Column(name = "CustomerRequestId", length = 36)
    private String customerRequestId;

    @Column(name = "IdNumber", length = 20)
    private String idNumber;

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "CustomerName", columnDefinition = "nvarchar(100)")
    private String customerName;

    @Column(name = "CompanyName", columnDefinition = "nvarchar(255)")
    private String companyName;

    @Column(name = "NumOfDependent")
    private Integer numOfDependent;

    @Column(name = "RequestData", columnDefinition = "nvarchar(510)")
    private String requestData;

    @Enumerated(STRING)
    @Column(name = "RequestStatus")
    private RequestStatus requestStatus;

    @Column(name = "RemoteAddr", length = 20)
    private String remoteAddr;

    @Column(name = "IsDuplicate", columnDefinition = "bit default 1")
    private boolean duplicate;

    @Column(name = "DuplicateWith", length = 36)
    private String duplicateWith;

    @Column(name = "FetchTimes", length = 1, columnDefinition = "int default 0")
    private Integer fetchTimes;

    @Temporal(TIMESTAMP)
    @Column(name = "LastFetch")
    private Date lastFetch;

    @Column(name = "ResponseId", length = 36, updatable = false, insertable = false)
    private String responseId;

    @Column(name = "IsCharged", columnDefinition = "bit default 0")
    private Boolean charged;

    @JoinColumn(name = "ResponseId")
    @ManyToOne(fetch = LAZY)
    private InfoDiscoveryResponse infoDiscoveryResponse;

    public InfoDiscoveryRequest() {
        this.fetchTimes = 0;
        this.duplicate = false;
        this.requestStatus = PENDING;
        this.charged = false;
    }

    public InfoDiscoveryRequest(MtInfoDiscoveryRequest mtRequest, String userId, String remoteAddr) {
        this();
        this.setPhoneNumber(mtRequest.getPhoneNumber());
        this.customerRequestId = mtRequest.getRequestId();
        this.remoteAddr = remoteAddr;
        this.createdBy = userId;
        this.idNumber = mtRequest.getIdNumber();
        this.requestData = JsonUtils.toJson(mtRequest);
        this.companyName = mtRequest.getCompanyName();
        this.customerName = mtRequest.getCustomerName();
        this.numOfDependent = StringUtils.isEmpty(mtRequest.getNumOfDependent())
                ? null
                : Integer.parseInt(mtRequest.getNumOfDependent());
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public VerifyService getVerifyService() {
        return verifyService;
    }

    public void setVerifyService(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getCustomerRequestId() {
        return customerRequestId;
    }

    public void setCustomerRequestId(String customerRequestId) {
        this.customerRequestId = customerRequestId;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.fetchTimes = 0;
        this.lastFetch = null;
        this.requestStatus = requestStatus;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

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
        if (!StringUtils.isEmpty(this.phoneNumber)) {
            this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getNumOfDependent() {
        return numOfDependent;
    }

    public void setNumOfDependent(Integer numOfDependent) {
        this.numOfDependent = numOfDependent;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public String getDuplicateWith() {
        return duplicateWith;
    }

    public void setDuplicateWith(String duplicateWith) {
        this.duplicateWith = duplicateWith;
    }

    public Integer getFetchTimes() {
        return fetchTimes;
    }

    public void setFetchTimes(Integer fetchTimes) {
        this.fetchTimes = fetchTimes;
    }

    public Date getLastFetch() {
        return lastFetch;
    }

    public void setLastFetch(Date lastFetch) {
        this.lastFetch = lastFetch;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public Boolean getCharged() {
        return charged;
    }

    public void setCharged(Boolean charged) {
        this.charged = charged;
        this.fetchTimes = 0;
        this.lastFetch = null;
    }

    public InfoDiscoveryResponse getInfoDiscoveryResponse() {
        return infoDiscoveryResponse;
    }

    public void setInfoDiscoveryResponse(InfoDiscoveryResponse idResponse) {
        this.infoDiscoveryResponse = idResponse;
    }
}
