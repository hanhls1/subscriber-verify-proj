//package vn.metech.schedule;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import vn.metech.constant.RequestStatus;
//import vn.metech.constant.Transaction;
//import vn.metech.dto.balance.BalanceTransactionMessage;
//import vn.metech.entity.InfoDiscoveryRequest;
//import vn.metech.entity.InfoDiscoveryResponse;
//import vn.metech.kafka.dwh.constant.DwhStatus;
//import vn.metech.kafka.sender.KafkaSender;
//import vn.metech.service.IInfoDiscoveryRequestService;
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
//	private IInfoDiscoveryRequestService infoDiscoveryRequestService;
//
//	public ProcessChargeableRequestSchedule(
//					KafkaSender kafkaSender,
//					IInfoDiscoveryRequestService infoDiscoveryRequestService,
//					@Value("${kafka.topic.balance-transaction.topics:balance-transaction}") String balanceTransactionTopics) {
//		this.kafkaSender = kafkaSender;
//		this.infoDiscoveryRequestService = infoDiscoveryRequestService;
//		this.balanceTransactionTopics = balanceTransactionTopics;
//	}
//
//	@Scheduled(cron = "${billing.balance-transaction.cron:0/20 * * * * *}")
//	public void syncBalanceTransactionToKafka() {
//		List<InfoDiscoveryRequest> chargeableRequests =
//						infoDiscoveryRequestService.getChargeableRequestsIncludeResponseWithLimit();
//		if (chargeableRequests.isEmpty()) {
//			return;
//		}
//		for (InfoDiscoveryRequest infoDiscoveryRequest : chargeableRequests) {
//			if (infoDiscoveryRequest.getInfoDiscoveryResponse() == null) {
//				continue;
//			}
//
//			BalanceTransactionMessage balanceTransactionMessage = new BalanceTransactionMessage(
//							infoDiscoveryRequest.getCreatedBy(),
//							infoDiscoveryRequest.getCustomerRequestId(),
//							getRequestTransaction(infoDiscoveryRequest),
//							infoDiscoveryRequest.getAppService(),
//							infoDiscoveryRequest.getVerifyService()
//			);
//
//			kafkaSender.send(
//							balanceTransactionTopics,
//							balanceTransactionMessage,
//							res -> {
//								infoDiscoveryRequest.setCharged(true);
//								infoDiscoveryRequestService.update(infoDiscoveryRequest);
//							},
//							err -> {
//								infoDiscoveryRequest.setCharged(false);
//								infoDiscoveryRequest.setFetchTimes(infoDiscoveryRequest.getFetchTimes() + 1);
//								infoDiscoveryRequest.setLastFetch(new Date());
//								infoDiscoveryRequestService.update(infoDiscoveryRequest);
//							}
//			);
//		}
//	}
//
//	private Transaction getRequestTransaction(InfoDiscoveryRequest infoDiscoveryRequest) {
//		Transaction transaction = Transaction.UN_HOLD;
//		if (infoDiscoveryRequest.getRequestStatus() == RequestStatus.ANSWER_RECEIVED) {
//			InfoDiscoveryResponse infoDiscoveryResponse = infoDiscoveryRequest.getInfoDiscoveryResponse();
//			if (infoDiscoveryResponse.getDwhStatus() == DwhStatus.SUCCESS) {
//				return Transaction.RELEASE;
//			}
//		}
//
//		return transaction;
//	}
//}
