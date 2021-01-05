package vn.metech.dto.query;

import vn.metech.constant.AppService;

import java.math.BigDecimal;

public class SummaryBalanceDetail {

	private AppService appService;
	private BigDecimal totalAmount;

	public SummaryBalanceDetail() {
	}

	public SummaryBalanceDetail(AppService appService, BigDecimal totalAmount) {
		this.appService = appService;
		this.totalAmount = totalAmount;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
