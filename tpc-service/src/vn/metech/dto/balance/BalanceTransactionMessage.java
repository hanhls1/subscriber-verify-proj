package vn.metech.dto.balance;

import vn.metech.constant.AppService;
import vn.metech.constant.Transaction;
import vn.metech.constant.VerifyService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BalanceTransactionMessage implements Serializable {

	private String notes;
	private String userId;
	private Date actionTime;
	private String requestId;
	private BigDecimal amount;
	private Transaction transaction;
	private AppService appService;
	private VerifyService verifyService;

	public BalanceTransactionMessage() {
	}

	public BalanceTransactionMessage(String userId, String requestId, BigDecimal amount, Transaction transaction) {
		this();
		this.userId = userId;
		this.requestId = requestId;
		this.amount = amount;
		this.transaction = transaction;
	}

	public BalanceTransactionMessage(
					String userId, String requestId, Transaction transaction,
					AppService appService, VerifyService verifyService) {
		this(userId, requestId, null, transaction);
		this.appService = appService;
		this.verifyService = verifyService;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
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
}