package vn.metech.dto.balance;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserBalanceTopUpRequest implements Serializable {

	private String topUpUserId;
	private BigDecimal amount;
	private String note;

	public String getTopUpUserId() {
		return topUpUserId;
	}

	public void setTopUpUserId(String topUpUserId) {
		this.topUpUserId = topUpUserId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
