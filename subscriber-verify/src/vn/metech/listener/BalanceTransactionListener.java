package vn.metech.listener;

import org.springframework.stereotype.Component;
import vn.metech.service.IBalanceService;

@Component
public class BalanceTransactionListener {

	private IBalanceService balanceService;

	public BalanceTransactionListener(IBalanceService balanceService) {
		this.balanceService = balanceService;
	}

//	@KafkaListener(
//					groupId = "${kafka.topic.balance-transaction.group-id:default}",
//					topics = "${kafka.topic.balance-transaction.topics:balance-transaction}",
//					containerFactory = "kafkaListenerContainerFactory")
//	public void balanceTransactionListening(
//					ConsumerRecord<String, String> consumerRecord) throws BalanceNotEnoughException {
//		BalanceTransactionMessage message = JsonUtils.toObject(consumerRecord.value(), BalanceTransactionMessage.class);
//		if (message == null || message.getTransaction() == null) {
//			return;
//		}
//		balanceService.saveBalance(message.getUserId(), message.getRequestId(),
//						message.getAppService(), message.getAmount(), message.getNotes(), message.getTransaction());
//	}

}
