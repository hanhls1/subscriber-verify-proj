package vn.metech.schedule;//package vn.metech.schedule;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import vn.metech.constant.AppService;
//import vn.metech.constant.RequestStatus;
//import vn.metech.constant.Transaction;
//import vn.metech.dto.balance.BalanceTransactionMessage;
//import vn.metech.entity.AdRequest;
//import vn.metech.entity.AdResponse;
//import vn.metech.kafka.mbf.MbfStatus;
//import vn.metech.kafka.sender.KafkaSender;
//import vn.metech.service.IAdRequestService;
//
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class ProcessChargeableRequestSchedule {
//
//	private final String balanceTransactionTopics;
//
//	private KafkaSender kafkaSender;
//	private IAdRequestService adRequestService;
//
//	public ProcessChargeableRequestSchedule(
//					KafkaSender kafkaSender,
//					IAdRequestService adRequestService,
//					@Value("${kafka.topic.balance-transaction.topics:balance-transaction}") String balanceTransactionTopics) {
//		this.kafkaSender = kafkaSender;
//		this.adRequestService = adRequestService;
//		this.balanceTransactionTopics = balanceTransactionTopics;
//	}
//
//	@Scheduled(cron = "${billing.balance-transaction.cron:0/20 * * * * *}")
//	public void syncBalanceTransactionToKafka() {
//		List<AdRequest> chargeableRequests = adRequestService.getChargeableRequestsIncludeResponseWithLimit();
//		if (chargeableRequests.isEmpty()) {
//			return;
//		}
//		for (AdRequest adRequest : chargeableRequests) {
//			if (adRequest.getAdResponse() == null) {
//				continue;
//			}
//			BalanceTransactionMessage balanceTransactionMessage = new BalanceTransactionMessage(
//							adRequest.getCreatedBy(),
//							adRequest.getCustomerRequestId(),
//							getRequestTransaction(adRequest),
//							AppService.AD_REFERENCE,
//							adRequest.getVerifyService()
//			);
//			kafkaSender.send(balanceTransactionTopics, balanceTransactionMessage,
//							res -> {
//								adRequest.setCharged(true);
//								adRequestService.update(adRequest);
//							},
//							err -> {
//								adRequest.setCharged(false);
//								adRequest.setFetchTimes(adRequest.getFetchTimes() + 1);
//								adRequest.setLastFetch(new Date());
//								adRequestService.update(adRequest);
//							}
//			);
//		}
//	}
//
//	private Transaction getRequestTransaction(AdRequest adRequest) {
//		Transaction transaction = Transaction.UN_HOLD;
//		if (adRequest.getRequestStatus() == RequestStatus.ANSWER_SENT
//						|| adRequest.getRequestStatus() == RequestStatus.ANSWER_RECEIVED) {
//			AdResponse adResponse = adRequest.getAdResponse();
//			if (adResponse.getMbfStatus() == MbfStatus.SUCCESS) {
//				return Transaction.RELEASE;
//			}
//		}
//
//		return transaction;
//	}
//}
