package vn.metech.dto;

import vn.metech.constant.Transaction;
import vn.metech.constant.VerifyService;

public class UserBalanceTransactionRequest {

	private String userId;
	private String referenceId;
	private String appService;
	private Transaction transaction;
	private VerifyService verifyService;

	public UserBalanceTransactionRequest() {
		this.appService = "AIO";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getAppService() {
		return appService;
	}

	public void setAppService(String appService) {
		this.appService = appService;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}
}
