package vn.metech.schedule;//package vn.metech.schedule;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import vn.metech.constant.*;
//import vn.metech.dto.response.MtAdResponse;
//import vn.metech.dto.response.MtCurrentImeiResponse;
//import vn.metech.dto.response.MtPastImeiResponse;
//import vn.metech.entity.AdRequest;
//import vn.metech.entity.AdResponse;
//import vn.metech.kafka.mbf.MbfStatus;
//import vn.metech.kafka.mbf.adref.MbfAdReferenceResult;
//import vn.metech.kafka.sender.KafkaSender;
//import vn.metech.redis.util.RedisUtil;
//import vn.metech.service.IAdRequestService;
//import vn.metech.shared.CustomerResult;
//import vn.metech.shared.CustomerResult.RequestResult;
//import vn.metech.shared.PartnerInfo;
//import vn.metech.util.JsonUtils;
//import vn.metech.util.StringUtils;
//
//import java.util.*;
//
//@Component
//public class ReplyToCustomerSchedule {
//
//	private final String SERVICE_NAME = "ReplyToCustomerSchedule > ";
//	private final String CRON_KEY = "${customer.reply.cron:0 * * * * *}";
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	private KafkaSender kafkaSender;
//	private String customerReplyTopics;
//	private String customerResultTopics;
//	private RedisUtil<PartnerInfo> redisUtil;
//	private IAdRequestService adRequestService;
//
//	public ReplyToCustomerSchedule(
//					KafkaSender kafkaSender,
//					RedisUtil<PartnerInfo> redisUtil,
//					IAdRequestService adRequestService,
//					@Value(Kafka.CUSTOMER_REPLY_TOPIC_KEY) String customerReplyTopics,
//					@Value(Kafka.CUSTOMER_RESULT_TOPIC_KEY) String customerResultTopics) {
//		this.redisUtil = redisUtil;
//		this.kafkaSender = kafkaSender;
//		this.customerReplyTopics = customerReplyTopics;
//		this.customerResultTopics = customerResultTopics;
//		this.adRequestService = adRequestService;
//	}
//
////	@Scheduled(cron = CRON_KEY)
////	@SchedulerLock(name = "ReplyToCustomerSchedule.ad.replyResultToCustomer()")
//	public void replyResultToCustomer() {
//
//		List<AdRequest> answerReceivedRequests =
//						adRequestService.getAnswerReceivedRequestsIncludeResponseWithLimit();
//
//		for (AdRequest adRequest : answerReceivedRequests) {
//			adRequest.setLastFetch(new Date());
//			adRequest.setFetchTimes(adRequest.getFetchTimes() + 1);
//			//
//			applyResponseToKafka(adRequest);
//		}
//	}
//
//	private void applyResponseToKafka(AdRequest adRequest) {
//		AdResponse adResponse = adRequest.getAdResponse();
//
//		if (adResponse == null) {
//			return;
//		}
//
//		CustomerResult customerResult = initPartnerResultFrom(adRequest, adResponse);
//
//		if (customerResult == null) {
//			return;
//		}
//
//		kafkaSender.send(customerReplyTopics, customerResult,
//						sendResult -> {
//							adRequest.setRequestStatus(RequestStatus.ANSWER_SENT);
//							if (adResponse.getResponseStatus() == ResponseStatus.ANSWER_RECEIVED) {
//								adResponse.setResponseStatus(ResponseStatus.ANSWER_SENT);
//							}
//
//							adRequestService.update(adRequest);
//						},
//						throwable -> adRequestService.update(adRequest)
//		);
//	}
//
//	private CustomerResult initPartnerResultFrom(
//					AdRequest adRequest, AdResponse adResponse) {
//
//		PartnerInfo partnerInfo = redisUtil
//						.getValue(CacheKey.Auth.partnerOf(adRequest.getCreatedBy()));
//
//		Map<String, String> headers = partnerHeaders(partnerInfo);
//		if (headers.isEmpty()) {
//
//			return null;
//		}
//
//		CustomerResult customerResult = new CustomerResult(customerResultTopics);
//		customerResult.setRequestResult(new RequestResult());
//		customerResult.setHeaders(headers);
//		customerResult.setAppService(AppService.AD_REFERENCE);
//		customerResult.setRequestUrl(partnerInfo.getUrl());
//		customerResult.setRequestId(adRequest.getId());
//		customerResult.setRequestBy(adRequest.getCreatedBy());
//		customerResult.getRequestResult().setResponseId(adResponse.getId());
//		customerResult.setCustomerRequestId(adRequest.getCustomerRequestId());
//		customerResult.getRequestResult().setPhoneNumber(adRequest.getPhoneNumber());
//
//		if (adRequest.getRequestStatus() == RequestStatus.ANSWER_RECEIVED) {
//			MbfAdReferenceResult adReferenceResult = JsonUtils
//							.toObject(adResponse.getResponseData(), MbfAdReferenceResult.class);
//
//			customerResult.getRequestResult().setStatus(adResponse.getMbfStatus().value());
//			customerResult.getRequestResult().setDescription(adResponse.getMbfStatus().message());
//			if (adRequest.getRequestType() == RequestType.CURRENT_CHECK) {
//				customerResult.getRequestResult().setResult(JsonUtils.toJson(
//								new MtAdResponse(
//												adRequest.getCustomerRequestId(),
//												adResponse.getId(),
//												adResponse.getMbfStatus(),
//												adRequest.getPhoneNumber(),
//												new MtCurrentImeiResponse(adReferenceResult.getCurrentImeiResult())
//								)
//				));
//			} else if (adRequest.getRequestType() == RequestType.PAST_CHECK) {
//				customerResult.getRequestResult().setResult(JsonUtils.toJson(
//								new MtAdResponse(
//												adRequest.getCustomerRequestId(),
//												adResponse.getId(),
//												adResponse.getMbfStatus(),
//												adRequest.getPhoneNumber(),
//												new MtPastImeiResponse(adReferenceResult.getLatestImeiResult())
//								)
//				));
//			}
//
//		} else {
//			customerResult.getRequestResult().setStatus(MbfStatus.TIMEOUT.value());
//			customerResult.getRequestResult().setDescription(MbfStatus.TIMEOUT.message());
//		}
//
//		return customerResult;
//	}
//
//	private Map<String, String> partnerHeaders(PartnerInfo partnerInfo) {
//
//		if (partnerInfo == null) {
//			return Collections.emptyMap();
//		}
//
//		Map<String, String> headers = new HashMap<>();
//
//		String basicAuthString = StringUtils
//						.toBase64(partnerInfo.getUsername() + ":" + partnerInfo.getPassword());
//
//		headers.put("Authorization", "Basic " + basicAuthString);
//		headers.put("Content-Type", "application/json");
//
//		return headers;
//	}
//}
