package vn.metech.entity;

import vn.metech.constant.TpcStatus;
import vn.metech.kafka.mbf.adref.AdResult;
import vn.metech.kafka.mbf.callref.CallResult;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TpcConfirm")
public class TpcConfirm extends BaseEntity {

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Column(name = "SubscriberImeiStatus", length = 100)
	private String subscriberImeiStatus;

	@Column(name = "SubscriberCallStatus", length = 100)
	private String subscriberCallStatus;

	@Column(name = "IdNumber", length = 20)
	private String idNumber;

	@Column(name = "RefPhone1")
	private String refPhone1;

	@Enumerated(EnumType.STRING)
	@Column(name = "RefPhone1Result")
	private CallResult refPhone1Result;

	@Column(name = "RefPhone2")
	private String refPhone2;

	@Enumerated(EnumType.STRING)
	@Column(name = "RefPhone2Result")
	private CallResult refPhone2Result;

	private Boolean callTimeout;

	@Enumerated(EnumType.STRING)
	@Column(name = "ImeiResult")
	private AdResult imeiResult;

	private Boolean imeiTimeout;

	@Column(name = "CheckedDate")
	@Temporal(TemporalType.DATE)
	private Date checkedDate;

	@Column(name = "CheckedDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkedDateTime;

	@Column(name = "CheckedImeiDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkedImeiDateTime;

	@Column(name = "CheckedImeiDate")
	@Temporal(TemporalType.DATE)
	private Date checkedImeiDate;

	@Column(name = "CheckedCallDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkedCallDateTime;

	@Column(name = "CheckedCallDate")
	@Temporal(TemporalType.DATE)
	private Date checkedCallDate;

	@Transient
	private TpcStatus tpcStatus;

	public TpcConfirm() {
		this.callTimeout = false;
		this.imeiTimeout = false;
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

	public String getSubscriberImeiStatus() {
		return subscriberImeiStatus;
	}

	public void setSubscriberImeiStatus(String subscriberImeiStatus) {
		this.subscriberImeiStatus = subscriberImeiStatus;
	}

	public String getSubscriberCallStatus() {
		return subscriberCallStatus;
	}

	public void setSubscriberCallStatus(String subscriberCallStatus) {
		this.subscriberCallStatus = subscriberCallStatus;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getRefPhone1() {
		return refPhone1;
	}

	public void setRefPhone1(String refPhone1) {
		this.refPhone1 = refPhone1;
	}

	public CallResult getRefPhone1Result() {
		return refPhone1Result;
	}

	public void setRefPhone1Result(CallResult refPhone1Result) {
		this.refPhone1Result = refPhone1Result;
	}

	public String getRefPhone2() {
		return refPhone2;
	}

	public void setRefPhone2(String refPhone2) {
		this.refPhone2 = refPhone2;
	}

	public CallResult getRefPhone2Result() {
		return refPhone2Result;
	}

	public void setRefPhone2Result(CallResult refPhone2Result) {
		this.refPhone2Result = refPhone2Result;
	}

	public AdResult getImeiResult() {
		return imeiResult;
	}

	public void setImeiResult(AdResult imeiResult) {
		this.imeiResult = imeiResult;
	}

	public void setTpcStatus(TpcStatus tpcStatus) {
		this.tpcStatus = tpcStatus;
	}

	public Boolean getCallTimeout() {
		return callTimeout;
	}

	public void setCallTimeout(Boolean callTimeout) {
		this.callTimeout = callTimeout;
	}

	public Boolean getImeiTimeout() {
		return imeiTimeout;
	}

	public void setImeiTimeout(Boolean imeiTimeout) {
		this.imeiTimeout = imeiTimeout;
	}

	public Date getCheckedImeiDateTime() {
		return checkedImeiDateTime;
	}

	public void setCheckedImeiDateTime(Date checkedImeiDateTime) {
		this.checkedImeiDateTime = checkedImeiDateTime;
	}

	public Date getCheckedImeiDate() {
		return checkedImeiDate;
	}

	public void setCheckedImeiDate(Date checkedImeiDate) {
		this.checkedImeiDate = checkedImeiDate;
	}

	public Date getCheckedCallDateTime() {
		return checkedCallDateTime;
	}

	public void setCheckedCallDateTime(Date checkedCallDateTime) {
		this.checkedCallDateTime = checkedCallDateTime;
	}

	public Date getCheckedCallDate() {
		return checkedCallDate;
	}

	public void setCheckedCallDate(Date checkedCallDate) {
		this.checkedCallDate = checkedCallDate;
	}

	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}

	public Date getCheckedDateTime() {
		return checkedDateTime;
	}

	public void setCheckedDateTime(Date checkedDateTime) {
		this.checkedDateTime = checkedDateTime;
	}

	public TpcStatus getTpcStatus() {
		if (imeiResult == AdResult.MATCH
						|| refPhone1Result == CallResult.MATCH
						|| refPhone2Result == CallResult.MATCH) {
			return TpcStatus.PASS;
		}

		return TpcStatus.NOT_PASS;
	}
}
