package vn.metech.dto.balance;

import vn.metech.constant.AppService;
import vn.metech.constant.Transaction;
import vn.metech.constant.VerifyService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserBalanceTransactionRequestBill {

	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	private String referenceId;

	@NotNull
	private AppService appService;

	@NotNull
	private VerifyService verifyService;

	@NotNull
	private Transaction transaction;

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

	public void setVerifyService(Object verifyService) {
		if (verifyService instanceof String) {
			this.verifyService = VerifyService.valueOf((String) verifyService);
			if (this.verifyService == null) {
				this.verifyService = VerifyService.of((String) verifyService);
			}
		} else if (verifyService instanceof VerifyService) {
			this.verifyService = (VerifyService) verifyService;
		}
	}
}
