package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.dto.response.data.MfsMessage;
import vn.metech.entity.base.EntityBase;
import vn.metech.util.DateUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "message_aio", indexes = {
        @Index(columnList = "phone_number,customer_code,date_time_in_milliseconds,is_processed")
})
public class Message extends EntityBase {

    public Message() {
        this.processed = false;
    }

    public Message(MfsMessage mfsMessage) {
        this();
        this.customerCode = mfsMessage.getCustomerCode();
        this.content = mfsMessage.getContent();
        this.dateTime = DateUtils.parseDate(mfsMessage.getDateTime(), "yyyy-MM-dd HH:mm:ss");
        if (this.dateTime != null) {
            this.dateTimeInMilliseconds = this.dateTime.getTime();
        }
    }

    public Message(MfsMessage mfsMessage, ConfirmInfo confirmInfo) {
        this(mfsMessage);
        this.phoneNumber = confirmInfo.getPhoneNumber();
        this.confirmInfo = confirmInfo;
        this.customerCode = confirmInfo.getCustomerCode();
        this.setCreatedBy(confirmInfo.getCreatedBy());
    }

    public Message(MfsMessage mfsMessage, ConfirmInfo confirmInfo, int messageType) {
        this(mfsMessage, confirmInfo);
        this.messageType = messageType;
    }

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "customer_code", length = 50)
    private String customerCode;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @Column(name = "date_time_in_milliseconds")
    private Long dateTimeInMilliseconds;

    @Column(name = "message_type")
    private Integer messageType;

    @Column(name = "is_processed")
    private boolean processed;

    @Column(name = "confirm_info_id", length = 36, insertable = false, updatable = false)
    private String confirmInfoId;

    @JoinColumn(name = "confirm_info_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ConfirmInfo confirmInfo;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Long getDateTimeInMilliseconds() {
        return dateTimeInMilliseconds;
    }

    public void setDateTimeInMilliseconds(Long dateTimeInMilliseconds) {
        this.dateTimeInMilliseconds = dateTimeInMilliseconds;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getConfirmInfoId() {
        return confirmInfoId;
    }

    public void setConfirmInfoId(String confirmInfoId) {
        this.confirmInfoId = confirmInfoId;
    }

    public ConfirmInfo getConfirmInfo() {
        return confirmInfo;
    }

    public void setConfirmInfo(ConfirmInfo confirmInfo) {
        this.confirmInfo = confirmInfo;
    }
}
