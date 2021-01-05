package vn.metech.dto.query;

import com.fasterxml.jackson.core.type.TypeReference;
import vn.metech.constant.AppService;
import vn.metech.util.JsonUtils;

import java.math.BigDecimal;
import java.util.*;

public class UserBalanceSummary {

	private String userId;
	private Date summaryDate;
	private BigDecimal totalAmount;
	private Map<AppService, SummaryBalanceDetail> summaryBalanceDetails;

	public UserBalanceSummary() {
		this.totalAmount = BigDecimal.ZERO;
		this.summaryBalanceDetails = new HashMap<>();
	}

	public UserBalanceSummary(String userId, Date summaryDate) {
		this();
		this.userId = userId;
		this.summaryDate = summaryDate;
	}

	public static List<UserBalanceSummary> of(List objects) {
		try {
			Map<String, UserBalanceSummary> userBalanceSummariesMap = new HashMap<>();
			List<UserBalanceSummary> userBalanceSummaries = new ArrayList<>();
			for (Object object : objects) {
				Object[] fields = JsonUtils.convert(object, new TypeReference<Object[]>() {
				});
				String userId = null;
				AppService appService = null;
				BigDecimal amount = null;
				Date summaryDate = null;
				for (int i = 0; i < fields.length; i++) {
					if (i == 0) {
						userId = (String) fields[i];
					} else if (i == 1) {
						appService = AppService.valueOf((String) fields[i]);
					} else if (i == 2) {
						amount = (BigDecimal) fields[i];
					} else if (i == 3) {
						summaryDate = (Date) fields[i];
					}
				}
				UserBalanceSummary userBalanceSummary = userBalanceSummariesMap.get(userId);
				if (userBalanceSummary == null) {
					userBalanceSummary = new UserBalanceSummary(userId, summaryDate);
					userBalanceSummariesMap.put(userId, userBalanceSummary);
					userBalanceSummaries.add(userBalanceSummary);
				}
				SummaryBalanceDetail summaryBalanceDetail = userBalanceSummary.getSummaryBalanceDetails().get(appService);
				if (summaryBalanceDetail == null) {
					summaryBalanceDetail = new SummaryBalanceDetail(appService, amount);
					userBalanceSummary.getSummaryBalanceDetails().put(appService, summaryBalanceDetail);
				}
				userBalanceSummary.setSummaryDate(summaryDate);
				userBalanceSummary.setTotalAmount(userBalanceSummary.totalAmount.add(amount));
			}
			return userBalanceSummaries;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getSummaryDate() {
		return summaryDate;
	}

	public void setSummaryDate(Date summaryDate) {
		this.summaryDate = summaryDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Map<AppService, SummaryBalanceDetail> getSummaryBalanceDetails() {
		return summaryBalanceDetails;
	}

	public void setSummaryBalanceDetails(Map<AppService, SummaryBalanceDetail> summaryBalanceDetails) {
		this.summaryBalanceDetails = summaryBalanceDetails;
	}
}
