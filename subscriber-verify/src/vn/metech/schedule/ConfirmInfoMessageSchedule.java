package vn.metech.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.metech.common.MessageStatus;
import vn.metech.common.RecordStatus;
import vn.metech.entity.ConfirmInfo;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;
import vn.metech.service.MfsMessageService;
import vn.metech.util.DateUtils;

import java.util.Date;

/**
 * GỬI TIN NHĂN CONFIRM INFO
 */
@Component
public class ConfirmInfoMessageSchedule {

    private final MfsMessageService mfsMessageService;
    private final ConfirmInfoCrudRepository confirmInfoCrudRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ConfirmInfoMessageSchedule(
            MfsMessageService mfsMessageService,
            ConfirmInfoCrudRepository confirmInfoCrudRepository) {
        this.mfsMessageService = mfsMessageService;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
    }

//    @Scheduled(cron = "0/30 * * * * *")
//    public void fetchAndSaveMessage() {
//        RecordStatus recordStatus = RecordStatus.REQUESTING;
//        List<MessageStatus> messageStatuses = Arrays.asList(
//                MessageStatus.REQUEST_SENT, MessageStatus.CUSTOMER_PENDING, MessageStatus.SYNTAX_ERROR
//        );
//        long counter = confirmInfoCrudRepository
//                .countByRecordStatusAndMessageStatusIn(recordStatus, messageStatuses);
//        if (counter <= 0) return;
//
//        Page<ConfirmInfo> confirmInfoLst = confirmInfoCrudRepository.getByRecordStatusAndMessageStatusIn(
//                recordStatus, messageStatuses, PageRequest.of(0, 32, Sort.by(Sort.Direction.ASC, "createdDate"))
//        );
//        ExecutorService executorService = Executors.newFixedThreadPool(16);
//        while (!confirmInfoLst.isEmpty()) {
//            for (ConfirmInfo confirmInfo : confirmInfoLst) {
//                executorService.submit(() -> {
//                    mfsMessageService.getAndSaveMessage(confirmInfo, 0);
//                    mfsMessageService.getAndSaveMessage(confirmInfo, 1);
//                });
//            }
//            if (!confirmInfoLst.hasNext()) break;
//
//            confirmInfoLst = confirmInfoCrudRepository.getByRecordStatusAndMessageStatusIn(
//                    recordStatus, messageStatuses, confirmInfoLst.nextPageable()
//            );
//        }
//    }

//    @Scheduled(cron = "0/30 * * * * *")
    @Scheduled(cron = "${mbf.process.confirm.cron:0/30 * * * * *}")
    public void processConfirmInfoMessages() {
        logger.info("[processConfirmInfoMessages]");
        RecordStatus recordStatus = RecordStatus.REQUESTING;
        long numberOfRecords = confirmInfoCrudRepository.countByRecordStatus(recordStatus);
        if (numberOfRecords <= 0) return;
        Page<ConfirmInfo> confirmInfoLst = confirmInfoCrudRepository.findMessageProcessibleBy(
                recordStatus,
                PageRequest.of(0, 32, Sort.by(Sort.Direction.ASC, "createdDate"))
        );

        Date now = new Date();
        while (!confirmInfoLst.isEmpty()) {
            for (ConfirmInfo confirmInfo : confirmInfoLst) {
                Date validTime = DateUtils.addDay(confirmInfo.getCreatedDate(), 2);
                if (now.compareTo(validTime) > 0) {
                    confirmInfo.setRecordStatus(RecordStatus.NO_FETCH_DATA);
                    confirmInfo.setMessageStatus(MessageStatus.CUSTOMER_TIMEOUT);
                    confirmInfo.setAggregateStatus(69);
                    confirmInfoCrudRepository.save(confirmInfo);

                    continue;
                }
                mfsMessageService.getAndSaveMessage(confirmInfo, 0);
                mfsMessageService.getAndSaveMessage(confirmInfo, 1);
            }
            System.gc();
            if (!confirmInfoLst.hasNext()) break;

            confirmInfoLst = confirmInfoCrudRepository
                    .findMessageProcessibleBy(recordStatus, confirmInfoLst.nextPageable());
        }
    }

}
