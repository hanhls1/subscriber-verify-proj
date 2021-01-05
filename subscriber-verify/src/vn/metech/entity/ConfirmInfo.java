package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.common.*;
import vn.metech.dto.request.RequestBase;
import vn.metech.entity.base.EntityBase;
import vn.metech.entity.base.IFetchable;
import vn.metech.exception.aio.ParamInvalidException;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "confirm_info", indexes = {
        @Index(columnList = "fetchable"), @Index(columnList = "request_id"),
        @Index(columnList = "q_request_id"), @Index(columnList = "created_by"),
        @Index(columnList = "aggregate_status"), @Index(columnList = "phone_number")
})
public class ConfirmInfo extends EntityBase implements IFetchable {

    @Enumerated(EnumType.STRING)
    @Column(name = "command", length = 36)
    private Command command;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", length = 100)
    private ServiceType serviceType;

    @Column(name = "service_code", length = 100)
    private String serviceCode;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "request_id", length = 36)
    private String requestId;

    @Column(name = "q_request_id", length = 36)
    private String qRequestId;

    @Column(name = "customer_code", length = 50)
    private String customerCode;

    @Column(name = "number_of_params", length = 2)
    private int numberOfParams;

    @Column(name = "number_of_results", length = 2)
    private int numberOfResults;

    @Column(name = "fetch_times", length = 2)
    private int fetchTimes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_fetch")
    private Date lastFetch;

    @Column(name = "fetchable")
    private boolean fetchable;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status", length = 36)
    private RecordStatus recordStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status", length = 36)
    private MessageStatus messageStatus;

    @Column(name = "secure_hash", length = 64)
    private String secureHash;

    @Column(name = "is_fee_charged")
    private boolean feeCharged;

    @Column(name = "aggregate_status") // -1 not_init, 0 inited, 1 updated, 2 timeout
    private Integer aggregateStatus;

	@Column(name = "account")
	private String account;

	@Column(name = "secure_key")
	private String secureKey;

	//created
    @Column(name = "sub_partner_name", columnDefinition = "nvarchar(100)")
	private String subPartnerName;

    @Column(name = "sub_partner_id", length = 36)
    private String subPartnerId;

    @Column(name = "partner_id", length = 36)
	private String partnerId;

    @Column(name = "partner_name", columnDefinition = "nvarchar(100)")
    private String partnerName;

	//thêm field: statusCode, status
	@Column(name = "status_code")
    private Integer statusCode;

	@Column(name = "status", columnDefinition = "nvarchar(75)")
    private String status;

	@JoinColumn(name = "reference_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	private ConfirmInfo confirmInfo;

    @MapKey(name = "param")
    @OneToMany(mappedBy = "confirmInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Map<Param, ConfirmParam> params;

    @MapKey(name = "qRequestId")
    @OneToMany(mappedBy = "confirmInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Map<String, ConfirmInfoReceive> confirmInfoReceives;

    public ConfirmInfo() {
        this.fetchable = true;
        this.fetchTimes = 0;
        this.numberOfResults = 0;
        this.numberOfParams = 0;
        this.requestId = StringUtils._3tId();
        this.params = new HashMap<>();
        this.confirmInfoReceives = new HashMap<>();
        this.recordStatus = RecordStatus.PENDING;
        this.messageStatus = MessageStatus.UNKNOWN;
        this.feeCharged = false;
        this.aggregateStatus = -1;
    }

    public ConfirmInfo(RequestBase requestBase, String userId, String customerCode, String subPartnerName, String partnerName) throws ParamInvalidException {
        this();

        Map<Param, Object> params = requestBase.params();
        this.phoneNumber = String.valueOf(params.get(Param.PHONE_NUMBER));
        this.secureHash = requestBase.getSecureHash();
        this.command = requestBase.getCommand();
        this.serviceType = requestBase.getServiceType();
        this.numberOfParams = params.size();

        //created
        this.subPartnerId = requestBase.getSubPartnerId();
        this.partnerId = requestBase.getPartnerId();
        this.serviceCode = this.serviceType.code();
        this.customerCode = customerCode;
        this.subPartnerName = subPartnerName;
        this.partnerName = partnerName;
        String requestId = requestBase.id();
        if (!StringUtils.isEmpty(requestId)) {
            this.requestId = requestId;
        }

        setCreatedBy(userId);
        for (Param param : this.serviceType.requiredParams()) {
            if (params.get(param) == null) {
                throw new ParamInvalidException(requestBase, this.requestId, param);
            }
        }
        params.forEach((param, value) -> this.params.put(param, new ConfirmParam(param, String.valueOf(value), this)));
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getParamValue(Param param) {
        ConfirmParam confirmParam = params.get(param);
        if (confirmParam == null || StringUtils.isEmpty(confirmParam.getValue())) {
            return null;
        }

        return confirmParam.getValue();
    }

    public ConfirmInfo(RequestBase requestBase, String userId) throws ParamInvalidException {
        this(requestBase, userId, null, null, null);
    }

    public ConfirmInfo(RequestBase requestBase) throws ParamInvalidException {
        this(requestBase, null);
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

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

    public int getNumberOfParams() {
        return numberOfParams;
    }

    public void setNumberOfParams(int numberOfParams) {
        this.numberOfParams = numberOfParams;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    @Override
    public int getFetchTimes() {
        return fetchTimes;
    }

    public void setFetchTimes(int fetchTimes) {
        this.fetchTimes = fetchTimes;
    }

    @Override
    public Date getLastFetch() {
        return lastFetch;
    }

    public void setLastFetch(Date lastFetch) {
        this.lastFetch = lastFetch;
    }

    @Override
    public boolean isFetchable() {
        return fetchable;
    }

    @Override
    public void resetFetch() {
        this.fetchTimes = 0;
        this.lastFetch = null;
    }

    public void setFetchable(boolean fetchable) {
        this.fetchable = fetchable;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
        if (recordStatus != null) {
            this.resetFetch();
        }
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getSecureHash() {
        return secureHash;
    }

    public void setSecureHash(String secureHash) {
        this.secureHash = secureHash;
    }

    public boolean isFeeCharged() {
        return feeCharged;
    }

    public void setFeeCharged(boolean feeCharged) {
        this.feeCharged = feeCharged;
        this.resetFetch();
    }

    public Integer getAggregateStatus() {
        return aggregateStatus;
    }

    public void setAggregateStatus(Integer aggregateStatus) {
        this.aggregateStatus = aggregateStatus;
    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public ConfirmInfo getConfirmInfo() {
		return confirmInfo;
	}

    public void setConfirmInfo(ConfirmInfo confirmInfo) {
        this.confirmInfo = confirmInfo;
    }

    public Map<Param, ConfirmParam> getParams() {
        return params;
    }

    public void setParams(Map<Param, ConfirmParam> params) {
        this.params = params;
    }

    public Map<String, ConfirmInfoReceive> getConfirmInfoReceives() {
        return confirmInfoReceives;
    }

    public void setConfirmInfoReceives(Map<String, ConfirmInfoReceive> confirmInfoReceives) {
        this.confirmInfoReceives = confirmInfoReceives;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubPartnerName() {
        return subPartnerName;
    }

    public void setSubPartnerName(String subPartnerName) {
        this.subPartnerName = subPartnerName;
    }

    public String getSubPartnerId() {
        return subPartnerId;
    }

    public void setSubPartnerId(String subPartnerId) {
        this.subPartnerId = subPartnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
