package vn.metech.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import vn.metech.constant.AppService;
import vn.metech.dto.msg.TpcMessage;
import vn.metech.entity.TpcConfirm;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.service.TpcConfirmInfoService;
import vn.metech.service.TpcConfirmService;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

@Component
public class TpcRequestSyncListener {

	private TpcConfirmService tpcConfirmService;
	private TpcConfirmInfoService tpcConfirmInfoService;

	public TpcRequestSyncListener(
					TpcConfirmService tpcConfirmService,
					TpcConfirmInfoService tpcConfirmInfoService) {
		this.tpcConfirmService = tpcConfirmService;
		this.tpcConfirmInfoService = tpcConfirmInfoService;
	}

	@KafkaListener(
					groupId = "${kafka.tpc.request-sync.group-id:default}",
					topics = "${kafka.tpc.request-sync.topics:tpc-request-sync}",
					containerFactory = "kafkaListenerContainerFactory")
	public void onResponseReceived(ConsumerRecord<String, String> consumerRecord) {
		TpcMessage tpcMessage = JsonUtils.toObject(consumerRecord.value(), TpcMessage.class);
		if (tpcMessage == null || tpcMessage.getRequestStatus() == null || tpcMessage.getAppService() == null) {
			return;
		}
		TpcConfirm tpcConfirm = tpcConfirmService.getConfirmBy(tpcMessage);
		if (tpcMessage.getAppService() == AppService.CALL_REFERENCE) {
			tpcConfirm.setCallTimeout(tpcMessage.getTimeout());
			tpcConfirm.setRefPhone1(tpcMessage.getRefPhone1());
			tpcConfirm.setRefPhone2(tpcMessage.getRefPhone2());
			tpcConfirm.setRefPhone1Result(tpcMessage.getRefPhone1Result());
			tpcConfirm.setRefPhone2Result(tpcMessage.getRefPhone2Result());
			tpcConfirm.setCheckedCallDate(tpcMessage.getCreatedDate());
			tpcConfirm.setCheckedCallDateTime(tpcMessage.getCreatedDate());
			if (!StringUtils.isEmpty(tpcMessage.getSubscriberStatus())) {
				tpcConfirm.setSubscriberCallStatus(tpcMessage.getSubscriberStatus());
			}
		} else if (tpcMessage.getAppService() == AppService.AD_REFERENCE) {
			tpcConfirm.setImeiTimeout(tpcMessage.getTimeout());
			tpcConfirm.setImeiResult(tpcMessage.getImeiResult());
			tpcConfirm.setCheckedImeiDate(tpcMessage.getCreatedDate());
			tpcConfirm.setCheckedImeiDateTime(tpcMessage.getCreatedDate());
			if (!StringUtils.isEmpty(tpcMessage.getSubscriberStatus())) {
				tpcConfirm.setSubscriberImeiStatus(tpcMessage.getSubscriberStatus());
			}
		}
		TpcConfirmInfo tpcConfirmInfo = tpcConfirmInfoService.getConfirmInfoBy(tpcMessage, tpcConfirm);
		tpcConfirmInfo.setRequestStatus(tpcMessage.getRequestStatus());
		tpcConfirmInfo.setSubscriberStatus(tpcMessage.getSubscriberStatus());
		tpcConfirmService.update(tpcConfirm);
		tpcConfirmInfoService.update(tpcConfirmInfo);
	}
}
