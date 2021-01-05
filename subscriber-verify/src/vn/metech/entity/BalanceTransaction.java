package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.AppService;
import vn.metech.constant.Transaction;
import vn.metech.constant.TransactionStatus;
import vn.metech.shared.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BalanceTransaction")
@Where(clause = "IsDeleted = 0")
public class BalanceTransaction extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "AppService")
	private AppService appService;

	@Enumerated(EnumType.STRING)
	@Column(name = "[Transaction]")
	private Transaction transaction;

	@Column(name = "Amount")
	private BigDecimal amount;

	@Column(name = "UserId", length = 36)
	private String userId;

	@Column(name = "BalanceId", length = 36)
	private String balanceId;

	@Column(name = "IsSuccess", columnDefinition = "bit default 0")
	private boolean success;

	@Column(name = "[Status]")
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@Column(name = "Note", columnDefinition = "nvarchar(255)")
	private String note;

	@Column(name = "RequestId", length = 36)
	private String requestId;

	@Column(name = "ReferenceId", length = 36)
	private String referenceId;

	public BalanceTransaction() {
		this.success = true;
		this.transaction = Transaction.PAY;
		this.status = TransactionStatus.SUCCESS;
	}

	public BalanceTransaction(Balance balance) {
		this();
		this.userId = balance.getUserId();
		this.balanceId = balance.getId();
	}

	public BalanceTransaction(Balance balance, Transaction transaction, String requestId, BigDecimal amount) {
		this(balance);
		this.transaction = transaction;
		this.requestId = requestId;
		this.amount = amount;
	}

	public BalanceTransaction(Balance balance, Transaction transaction, String requestId, BigDecimal amount, String note) {
		this(balance, transaction, requestId, amount);
		this.note = note;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}
