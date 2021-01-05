package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.AppService;
import vn.metech.shared.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BalanceSummaryDetail")
@Where(clause = "IsDeleted = 0")
public class BalanceSummaryDetail extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "AppService", length = 50)
	private AppService appService;

	@Column(name = "BalanceSummaryId", length = 36, updatable = false, insertable = false)
	private String balanceSummaryId;

	@Column(name = "TotalAmount")
	private BigDecimal totalAmount;

	@JoinColumn(name = "BalanceSummaryId")
	@ManyToOne(fetch = FetchType.LAZY)
	private BalanceSummary balanceSummary;

	public BalanceSummaryDetail() {
		this.totalAmount = BigDecimal.ONE;
	}

	public BalanceSummaryDetail(AppService appService, BalanceSummary balanceSummary) {
		this();
		this.appService = appService;
		this.balanceSummary = balanceSummary;
	}

	public BigDecimal increase(BigDecimal amount) {
		setTotalAmount(this.totalAmount.add(amount));

		return this.getTotalAmount();
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public String getBalanceSummaryId() {
		return balanceSummaryId;
	}

	public void setBalanceSummaryId(String balanceSummaryId) {
		this.balanceSummaryId = balanceSummaryId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BalanceSummary getBalanceSummary() {
		return balanceSummary;
	}

	public void setBalanceSummary(BalanceSummary balanceSummary) {
		this.balanceSummary = balanceSummary;
	}
}
