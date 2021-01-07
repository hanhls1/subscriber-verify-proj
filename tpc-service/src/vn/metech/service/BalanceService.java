package vn.metech.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.metech.constant.AppService;
import vn.metech.constant.StatusCode;
import vn.metech.constant.Transaction;
import vn.metech.constant.VerifyService;
import vn.metech.dto.balance.UserBalanceReleaseRequest;
import vn.metech.dto.balance.UserBalanceTransactionRequest;
import vn.metech.shared.ActionResult;
import vn.metech.util.RestUtils;

@Service
@Transactional
public class BalanceService {

	private LoadBalancerClient loadBalancerClient;

	public BalanceService(LoadBalancerClient loadBalancerClient) {
		this.loadBalancerClient = loadBalancerClient;
	}

	private String chooseBillingServiceUrl(String serviceId) {
		ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);

		return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
	}

	public boolean checkBalance(VerifyService verifyService, String userId, String requestId) {
		try {
			UserBalanceTransactionRequest userBalanceTransactionRequest = new UserBalanceTransactionRequest();
			userBalanceTransactionRequest.setUserId(userId);
			userBalanceTransactionRequest.setAppService(AppService.AD_REFERENCE);
			userBalanceTransactionRequest.setTransaction(Transaction.HOLD);
			userBalanceTransactionRequest.setReferenceId(requestId);
			userBalanceTransactionRequest.setVerifyService(verifyService);

			String url = chooseBillingServiceUrl("BILLING-SERVICE") + "/billing/balance/payment-transaction";

			RestUtils.RestResponse<ActionResult> restResponse =
							RestUtils.post(url, userBalanceTransactionRequest, ActionResult.class);
			if (restResponse.getHttpStatus() != HttpStatus.OK || restResponse.getBody() == null) {
				return false;
			}
			ActionResult actionResult = restResponse.getBody();

			return actionResult.getStatusCode().equals(StatusCode.SUCCESS);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean releaseTransaction(String userId, String requestId) {
		try {
			String url = chooseBillingServiceUrl("BILLING-SERVICE") + "/billing/balance/release-transaction";
			RestUtils.RestResponse<ActionResult> restResponse =
							RestUtils.post(url, new UserBalanceReleaseRequest(userId, requestId), ActionResult.class);
			if (restResponse.getHttpStatus() != HttpStatus.OK || restResponse.getBody() == null) {
				return false;
			}
			ActionResult actionResult = restResponse.getBody();

			return actionResult.getStatusCode().equals(StatusCode.SUCCESS);
		} catch (Exception e) {
			return false;
		}
	}
}
