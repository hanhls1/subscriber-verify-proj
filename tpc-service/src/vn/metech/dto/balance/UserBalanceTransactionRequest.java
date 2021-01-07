package vn.metech.dto.balance;

import vn.metech.constant.AppService;
import vn.metech.constant.Transaction;
import vn.metech.constant.VerifyService;

public class UserBalanceTransactionRequest {

	private String userId;
	private String referenceId;
	private AppService appService;
	private Transaction transaction;
	private VerifyService verifyService;

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

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}
}
