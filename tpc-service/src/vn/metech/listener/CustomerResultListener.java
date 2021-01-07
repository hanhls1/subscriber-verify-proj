//package vn.metech.listener;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import vn.metech.constant.Kafka;
//import vn.metech.constant.RequestStatus;
//import vn.metech.constant.ResponseStatus;
//import vn.metech.entity.InfoDiscoveryRequest;
//import vn.metech.entity.InfoDiscoveryResponse;
//import vn.metech.kafka.listener.KafkaListener;
//import vn.metech.service.IInfoDiscoveryRequestService;
//import vn.metech.shared.ActionResult;
//import vn.metech.util.JsonUtils;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class CustomerResultListener {
//
//	private KafkaListener kafkaListener;
//	private IInfoDiscoveryRequestService infoDiscoveryRequestService;
//	private String customerResultTopics, customerResultGroup;
//
//	public CustomerResultListener(
//					KafkaListener kafkaListener,
//					IInfoDiscoveryRequestService infoDiscoveryRequestService,
//					@Value(Kafka.CUSTOMER_RESULT_TOPIC_KEY) String customerResultTopics,
//					@Value(Kafka.CUSTOMER_RESULT_GROUP_KEY) String customerResultGroup) {
//		this.kafkaListener = kafkaListener;
//		this.customerResultTopics = customerResultTopics;
//		this.customerResultGroup = customerResultGroup;
//		this.infoDiscoveryRequestService = infoDiscoveryRequestService;
//	}
//
//	@PostConstruct
//	public void initialize() {
//		kafkaListener.listenerMessages(
//						customerResultTopics,
//						customerResultGroup,
//						this::onMessagesListening
//		);
//	}
//
//	private void onMessagesListening(ConsumerRecords<String, String> consumerRecords) {
//		consumerRecords.forEach(this::onMessageListening);
//	}
//
//	private void onMessageListening(ConsumerRecord<String, String> consumerRecord) {
//		ActionResult actionResult = JsonUtils
//						.toObject(consumerRecord.value(), ActionResult.class);
//
//		if (actionResult == null || actionResult.getResult() == null) {
//			return;
//		}
//
//		String requestId = String.valueOf(actionResult.getResult());
//		InfoDiscoveryRequest infoDiscoveryRequest = infoDiscoveryRequestService
//						.getRequestIncludeResponseBy(requestId, RequestStatus.ANSWER_SENT);
//
//		if (infoDiscoveryRequest == null) {
//			return;
//		}
//
//		infoDiscoveryRequest.setRequestStatus(RequestStatus.CLOSED);
//		InfoDiscoveryResponse infoDiscoveryResponse = infoDiscoveryRequest.getInfoDiscoveryResponse();
//		if (infoDiscoveryResponse != null
//						&& infoDiscoveryResponse.getResponseStatus() == ResponseStatus.ANSWER_SENT) {
//			infoDiscoveryRequest.getInfoDiscoveryResponse().setResponseStatus(ResponseStatus.CLOSED);
//		}
//
//		infoDiscoveryRequestService.update(infoDiscoveryRequest);
//	}
//}
