package vn.metech.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.metech.common.RecordStatus;
import vn.metech.common.ServiceType;
import vn.metech.entity.ConfirmInfo;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;
import vn.metech.service.DwhRequestService;
import vn.metech.service.MfsRequestService;
import vn.metech.util.DateUtils;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class ConfirmInfoRequestSchedule {


    private final ConfirmInfoCrudRepository confirmInfoCrudRepository;
    private final MfsRequestService mfsRequestService;
    private final DwhRequestService dwhRequestService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ConfirmInfoRequestSchedule(
            MfsRequestService mfsRequestService,
            DwhRequestService dwhRequestService,
            ConfirmInfoCrudRepository confirmInfoCrudRepository) {
        this.mfsRequestService = mfsRequestService;
        this.dwhRequestService = dwhRequestService;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
    }

    @Scheduled(cron = "${job.partner-request.mfs.send-request.cron:0/20 * * * * *}")
    public void sendRequestToMfs() {
        logger.info("[sendRequestToMfs]");
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));
        Page<String> confirmInfoIds = confirmInfoCrudRepository
                .findIdsByRecordStatusAndLastFetch(RecordStatus.PENDING, date, pageable);

        while (!confirmInfoIds.isEmpty()) {
            Iterable<ConfirmInfo> confirmInfos = confirmInfoCrudRepository.findIncludeParamsByIdIn(confirmInfoIds.stream().collect(Collectors.toList()));
            for (ConfirmInfo confirmInfo : confirmInfos) {
                confirmInfo.setLastFetch(new Date());
                confirmInfo.setFetchTimes(confirmInfo.getFetchTimes() + 1);
                confirmInfoCrudRepository.save(confirmInfo);
                if (confirmInfo.getServiceType() == ServiceType.FC_IMEI
                        || confirmInfo.getServiceType() == ServiceType.FC_BS_IMEI_02
                        || confirmInfo.getServiceType() == ServiceType.FC_BS_IMEI_01
                        || confirmInfo.getServiceType() == ServiceType.FC_TPC_ID
                        || confirmInfo.getServiceType() == ServiceType.FC_TPC_KYC_03
                        || confirmInfo.getServiceType() == ServiceType.FC_TPC_ADV_CAC
                        || confirmInfo.getServiceType() == ServiceType.FC_KYC_03
                        || confirmInfo.getServiceType() == ServiceType.FC_BS_CAC_01
                        || confirmInfo.getServiceType() == ServiceType.FC_ADV_CAC
                        || confirmInfo.getServiceType() == ServiceType.FC_REF_PHONE
                        || confirmInfo.getServiceType() == ServiceType.FC_ADV_REF_PHONE
                        || confirmInfo.getServiceType() == ServiceType.FC_TPC_REF_PHONE
                        || confirmInfo.getServiceType() == ServiceType.FC_ADV_CB01
                        || confirmInfo.getServiceType() == ServiceType.FC_ADV_CB02
                        || confirmInfo.getServiceType() == ServiceType.FC_ADV_CB03
                        || confirmInfo.getServiceType() == ServiceType.FC_BS_CB04/*
							|| confirmInfo.getServiceType() == ServiceType.FC_BS_CB05*/) {
                    mfsRequestService.sendRequest(confirmInfo);
                } else if (confirmInfo.getServiceType() == ServiceType.FC_KYC02_VPB
                        || confirmInfo.getServiceType() == ServiceType.FC_TPC_KYC_02) {
                    dwhRequestService.sendRequest(confirmInfo);
                }
            }
            System.gc();
            if (!confirmInfoIds.hasNext()) break;
            confirmInfoIds = confirmInfoCrudRepository
                    .findIdsByRecordStatusAndLastFetch(RecordStatus.PENDING, date, confirmInfoIds.nextPageable());
        }
    }

    @Scheduled(cron = "${job.partner-request.mfs.fetch-request.cron:0/20 * * * * *}")
    public void fetchAnsweredRequests() {
        mfsRequestService.fetchAnswers();

//        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdDate"));
//        Page<ConfirmInfo> confirmInfos = confirmInfoCrudRepository
//                .findByRecordStatusAndMessageStatus(RecordStatus.REQUESTING, MessageStatus.ACCEPTED, pageable);
//
//        while (!confirmInfos.isEmpty()) {
//            for (ConfirmInfo confirmInfo : confirmInfos) {
//                RequestWithKey rwk = new RequestWithKey(confirmInfo.getCustomerCode(), confirmInfo.getAccount(), confirmInfo.getSecureKey());
//                mfsRequestService.fetchAnswer(rwk, confirmInfo.getqRequestId());
//            }
//
//            if (!confirmInfos.hasNext()) break;
//            confirmInfos = confirmInfoCrudRepository
//                    .findByRecordStatusAndMessageStatus(RecordStatus.REQUESTING, MessageStatus.ACCEPTED, confirmInfos.nextPageable());
//
//        }
    }
}
