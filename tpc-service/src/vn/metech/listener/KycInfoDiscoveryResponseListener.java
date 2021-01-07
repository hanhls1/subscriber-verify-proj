//package vn.metech.listener;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//import vn.metech.constant.RequestStatus;
//import vn.metech.constant.ResponseStatus;
//import vn.metech.entity.InfoDiscoveryResponse;
//import vn.metech.kafka.dwh.message.ResponseKafkaMsg;
//import vn.metech.service.IInfoDiscoveryRequestService;
//import vn.metech.service.IInfoDiscoveryResponseService;
//import vn.metech.util.JsonUtils;
//
//@Component
//public class KycInfoDiscoveryResponseListener {
//
//	private IInfoDiscoveryRequestService infoDiscoveryRequestService;
//	private IInfoDiscoveryResponseService infoDiscoveryResponseService;
//
//	public KycInfoDiscoveryResponseListener(
//					IInfoDiscoveryRequestService infoDiscoveryRequestService,
//					IInfoDiscoveryResponseService infoDiscoveryResponseService) {
//		this.infoDiscoveryRequestService = infoDiscoveryRequestService;
//		this.infoDiscoveryResponseService = infoDiscoveryResponseService;
//	}
//
//	@KafkaListener(
//					groupId = "${dwh.kafka.kyc-info-discovery-response.group-id:default}",
//					topics = "${dwh.kafka.kyc-info-discovery-response.topics:kyc-info-discovery-response}",
//					containerFactory = "kafkaListenerContainerFactory")
//	public void onResponseReceived(ConsumerRecord<String, String> consumerRecord) {
//		ResponseKafkaMsg kafkaMsg = JsonUtils.toObject(consumerRecord.value(), ResponseKafkaMsg.class);
//		if (kafkaMsg == null || kafkaMsg.getStatus() == null) {
//			return;
//		}
//		InfoDiscoveryResponse infoDiscoveryResponse =
//						infoDiscoveryResponseService.getQuestionSentResponseIncludeRequestsBy(kafkaMsg.getResponseId());
//		if (infoDiscoveryResponse == null || infoDiscoveryResponse.getInfoDiscoveryRequests() == null) {
//			return;
//		}
//		infoDiscoveryResponse.setDwhStatus(kafkaMsg.getStatus());
//		infoDiscoveryResponse.setResponseData(JsonUtils.toJson(kafkaMsg.getData()));
//		infoDiscoveryResponse.setResponseStatus(ResponseStatus.ANSWER_RECEIVED);
//		infoDiscoveryResponse.getInfoDiscoveryRequests().forEach((k, infoDiscoveryRequest) ->
//						infoDiscoveryRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED)
//		);
//		infoDiscoveryRequestService.batchUpdate(infoDiscoveryResponse.getInfoDiscoveryRequests().values());
//		infoDiscoveryResponseService.update(infoDiscoveryResponse);
//	}
//}
