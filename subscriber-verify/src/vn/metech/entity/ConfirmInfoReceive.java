package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.common.MfsStatus;
import vn.metech.common.ServiceType;
import vn.metech.entity.base.EntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "confirm_info_receive", indexes = {@Index(columnList = "q_request_id, status, is_deleted")})
public class ConfirmInfoReceive extends EntityBase {

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", length = 100)
    private ServiceType serviceType;

    @Column(name = "request_id", length = 36)
    private String requestId;

    @Column(name = "q_request_id", length = 36)
    private String qRequestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MfsStatus status;

    @Column(name = "data", columnDefinition = "nvarchar(4000)")
    private String data;

    @Column(name = "is_aggregated")
    private Boolean aggregated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "receive_date")
    private Date receiveDate;

    @JoinColumn(name = "confirm_info_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private ConfirmInfo confirmInfo;

    public ConfirmInfoReceive() {
        this.receiveDate = new Date();
    }

    public ConfirmInfoReceive(ConfirmInfo confirmInfo) {
        this();
        if (confirmInfo != null) {
            this.confirmInfo = confirmInfo;
            this.phoneNumber = confirmInfo.getPhoneNumber();
            this.serviceType = confirmInfo.getServiceType();
            this.requestId = confirmInfo.getRequestId();
            this.qRequestId = confirmInfo.getqRequestId();
            this.setCreatedBy(confirmInfo.getCreatedBy());
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getqRequestId() {
        return qRequestId;
    }

    public void setqRequestId(String qRequestId) {
        this.qRequestId = qRequestId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MfsStatus getStatus() {
        return status;
    }

    public void setStatus(MfsStatus status) {
        this.status = status;
    }

    public Boolean getAggregated() {
        return aggregated == null ? false : aggregated;
    }

    public void setAggregated(Boolean aggregated) {
        this.aggregated = aggregated;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public ConfirmInfo getConfirmInfo() {
        return confirmInfo;
    }

    public void setConfirmInfo(ConfirmInfo confirmInfo) {
        this.confirmInfo = confirmInfo;
    }

}
