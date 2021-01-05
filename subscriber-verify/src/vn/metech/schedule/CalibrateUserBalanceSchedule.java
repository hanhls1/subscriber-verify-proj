package vn.metech.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.service.IBalanceService;

@Component
@Transactional
public class CalibrateUserBalanceSchedule {

	private IBalanceService balanceService;

	public CalibrateUserBalanceSchedule(
					IBalanceService balanceService) {
		this.balanceService = balanceService;
	}

	//@Scheduled(cron = "30 * * * * *")
	public void calibrateBalance() {
		System.out.println("Calibrate Balance: " + balanceService.calibrateUserBalances());
	}
}
