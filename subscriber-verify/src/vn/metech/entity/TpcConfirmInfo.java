package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.common.MessageStatus;
import vn.metech.common.Result;
import vn.metech.common.TpcStatus;
import vn.metech.entity.base.EntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "tpc_confirm_info", indexes = {@Index(columnList = "phone_number,check_date")})
public class TpcConfirmInfo extends EntityBase {

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "imei_msg_status", length = 50)
    private MessageStatus imeiMessageStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "call_msg_status", length = 50)
    private MessageStatus callMessageStatus;

    @Column(name = "ref_phone_1", length = 20)
    private String refPhone1;

    @Column(name = "ref_phone_2", length = 20)
    private String refPhone2;

    @Enumerated(EnumType.STRING)
    @Column(name = "ref_phone1_result", length = 20)
    private Result refPhone1Result;

    @Enumerated(EnumType.STRING)
    @Column(name = "ref_phone2_result", length = 20)
    private Result refPhone2Result;

    @Enumerated(EnumType.STRING)
    @Column(name = "imei_result", length = 20)
    private Result imeiResult;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_date")
    private Date checkDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_date_time")
    private Date checkDateTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_imei_date")
    private Date checkImeiDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_imei_date_time")
    private Date checkImeiDateTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "check_call_date")
    private Date checkCallDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_call_date_time")
    private Date checkCallDateTime;

    @Transient
    private TpcStatus tpcStatus;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MessageStatus getImeiMessageStatus() {
        return imeiMessageStatus;
    }

    public void setImeiMessageStatus(MessageStatus imeiMessageStatus) {
        this.imeiMessageStatus = imeiMessageStatus;
    }

    public MessageStatus getCallMessageStatus() {
        return callMessageStatus;
    }

    public void setCallMessageStatus(MessageStatus callMessageStatus) {
        this.callMessageStatus = callMessageStatus;
    }

    public String getRefPhone1() {
        return refPhone1;
    }

    public void setRefPhone1(String refPhone1) {
        this.refPhone1 = refPhone1;
    }

    public String getRefPhone2() {
        return refPhone2;
    }

    public void setRefPhone2(String refPhone2) {
        this.refPhone2 = refPhone2;
    }

    public Result getRefPhone1Result() {
        return refPhone1Result;
    }

    public void setRefPhone1Result(Result refPhone1Result) {
        this.refPhone1Result = refPhone1Result;
    }

    public Result getRefPhone2Result() {
        return refPhone2Result;
    }

    public void setRefPhone2Result(Result refPhone2Result) {
        this.refPhone2Result = refPhone2Result;
    }

    public Result getImeiResult() {
        return imeiResult;
    }

    public void setImeiResult(Result imeiResult) {
        this.imeiResult = imeiResult;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getCheckDateTime() {
        return checkDateTime;
    }

    public void setCheckDateTime(Date checkDateTime) {
        this.checkDateTime = checkDateTime;
    }

    public Date getCheckImeiDate() {
        return checkImeiDate;
    }

    public void setCheckImeiDate(Date checkImeiDate) {
        this.checkImeiDate = checkImeiDate;
    }

    public Date getCheckImeiDateTime() {
        return checkImeiDateTime;
    }

    public void setCheckImeiDateTime(Date checkImeiDateTime) {
        this.checkImeiDateTime = checkImeiDateTime;
    }

    public Date getCheckCallDate() {
        return checkCallDate;
    }

    public void setCheckCallDate(Date checkCallDate) {
        this.checkCallDate = checkCallDate;
    }

    public Date getCheckCallDateTime() {
        return checkCallDateTime;
    }

    public void setCheckCallDateTime(Date checkCallDateTime) {
        this.checkCallDateTime = checkCallDateTime;
    }

    public TpcStatus getTpcStatus() {
        if (imeiResult == Result.MATCH
                || refPhone1Result == Result.MATCH
                || refPhone2Result == Result.MATCH) {
            return TpcStatus.PASS;
        }

        return TpcStatus.NOT_PASS;
    }

    public void setTpcStatus(TpcStatus tpcStatus) {
        this.tpcStatus = tpcStatus;
    }
}
