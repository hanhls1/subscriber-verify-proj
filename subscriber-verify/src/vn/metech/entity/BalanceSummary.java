package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.AppService;
import vn.metech.shared.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "BalanceSummary")
@Where(clause = "IsDeleted = 0")
public class BalanceSummary extends BaseEntity {

	@Column(name = "UserId", length = 36)
	private String userId;

	@Column(name = "Day")
	private Integer day;

	@Column(name = "Month")
	private Integer month;

	@Column(name = "Year")
	private Integer year;

	@Temporal(TemporalType.DATE)
	@Column(name = "SummaryDate")
	private Date summaryDate;

	@Column(name = "TotalAmount")
	private BigDecimal totalAmount;

	@MapKey(name = "appService")
	@OneToMany(mappedBy = "balanceSummary")
	private Map<AppService, BalanceSummaryDetail> balanceSummaryDetails;

	public BalanceSummary() {
		this.setSummaryDate(new Date());
		this.balanceSummaryDetails = new HashMap<>();
	}

	public BalanceSummary(String userId, Date summaryDate) {
		this();
		this.userId = userId;
		this.summaryDate = summaryDate;
	}

	public BalanceSummary(String userId) {
		this(userId, new Date());
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getSummaryDate() {
		return summaryDate;
	}

	public void setSummaryDate(Date summaryDate) {
		this.summaryDate = summaryDate;
		Calendar c = Calendar.getInstance();
		c.setTime(summaryDate);
		this.setDay(c.get(Calendar.DAY_OF_MONTH));
		this.setMonth(c.get(Calendar.MONTH));
		this.setYear(c.get(Calendar.YEAR));
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Map<AppService, BalanceSummaryDetail> getBalanceSummaryDetails() {
		return balanceSummaryDetails;
	}

	public void setBalanceSummaryDetails(Map<AppService, BalanceSummaryDetail> balanceSummaryDetails) {
		this.balanceSummaryDetails = balanceSummaryDetails;
	}
}
