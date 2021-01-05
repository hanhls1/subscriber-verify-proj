package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import vn.metech.constant.Auth;
import vn.metech.constant.Transaction;
import vn.metech.dto.balance.UserBalanceReleaseRequest;
import vn.metech.dto.balance.UserBalanceTopUpRequest;
import vn.metech.dto.balance.UserBalanceTransactionRequest;
import vn.metech.exception.balance.BalanceNotEnoughException;
import vn.metech.exception.balance.TransactionInvalidException;
import vn.metech.service.IBalanceService;
import vn.metech.shared.ActionResult;

import javax.validation.Valid;

@RestController
@RequestMapping("/billing/balance")
public class BalanceController {

	private IBalanceService balanceService;

	public BalanceController(IBalanceService balanceService) {
		this.balanceService = balanceService;
	}

	@PostMapping("/top-up")
	public ActionResult topUpBalanceTo(
					@RequestHeader(Auth.USER_KEY) String userId,
					@RequestHeader(Auth.IP_ADDRESS_KEY) String remoteAddr,
					@Valid @RequestBody UserBalanceTopUpRequest topUpRequest) throws BalanceNotEnoughException {

		return new ActionResult(
						balanceService.increaseBalanceOf(
										topUpRequest.getTopUpUserId(), "TOP_UP", null,
										topUpRequest.getAmount(), topUpRequest.getNote()
						));
	}

	@PostMapping("/payment-transaction")
	public ActionResult checkBalanceOf(
					@Valid @RequestBody UserBalanceTransactionRequest checkRequest) throws BalanceNotEnoughException {
		return new ActionResult(balanceService.paymentTransaction(checkRequest));
	}

	@PostMapping("/release-transaction")
	public ActionResult releaseHoldTransaction(
					@RequestBody @Valid UserBalanceReleaseRequest userBalanceReleaseRequest) throws TransactionInvalidException {
		return new ActionResult(balanceService.releaseTransaction(
						userBalanceReleaseRequest.getUserId(),
						userBalanceReleaseRequest.getRequestId(),
						Transaction.RELEASE
		));
	}
}
