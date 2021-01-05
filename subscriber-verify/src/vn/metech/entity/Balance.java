package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.shared.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Balance")
@Where(clause = "IsDeleted = 0")
public class Balance extends BaseEntity {

	@Column(name = "UserId", length = 36)
	private String userId;

	@Column(name = "Balance")
	private BigDecimal balance;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastCalibration")
	private Date lastCalibration;

	@Column(name = "LastCalibrationAmount")
	private BigDecimal lastCalibrationAmount;

	public Balance() {
		this.balance = BigDecimal.valueOf(0);
		this.lastCalibrationAmount = this.balance;
	}

	public Balance(String userId) {
		this();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getLastCalibration() {
		return lastCalibration;
	}

	public void setLastCalibration(Date lastCalibration) {
		this.lastCalibration = lastCalibration;
	}

	public BigDecimal getLastCalibrationAmount() {
		return lastCalibrationAmount;
	}

	public void setLastCalibrationAmount(BigDecimal lastCalibrationAmount) {
		this.lastCalibrationAmount = lastCalibrationAmount;
	}
}
