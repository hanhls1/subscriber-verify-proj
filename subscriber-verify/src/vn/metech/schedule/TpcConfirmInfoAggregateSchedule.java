package vn.metech.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.metech.common.MessageStatus;
import vn.metech.common.Param;
import vn.metech.common.Result;
import vn.metech.common.ServiceType;
import vn.metech.dto.response.data.*;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.repository.TpcConfirmInfoRepository;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;
import vn.metech.repository.jpa.ConfirmInfoReceiveCrudRepository;
import vn.metech.repository.jpa.TpcConfirmInfoCrudRepository;
import vn.metech.service.LocalService;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TpcConfirmInfoAggregateSchedule {

    LocalService localService;
    TpcConfirmInfoRepository tpcConfirmInfoRepository;
    TpcConfirmInfoCrudRepository tpcConfirmInfoCrudRepository;
    ConfirmInfoCrudRepository confirmInfoCrudRepository;
    ConfirmInfoReceiveCrudRepository confirmInfoReceiveCrudRepository;
    int duplicateInDays;

    public TpcConfirmInfoAggregateSchedule(
            LocalService localService,
            TpcConfirmInfoRepository tpcConfirmInfoRepository,
            TpcConfirmInfoCrudRepository tpcConfirmInfoCrudRepository,
            ConfirmInfoCrudRepository confirmInfoCrudRepository,
            ConfirmInfoReceiveCrudRepository confirmInfoReceiveCrudRepository,
            @Value("${mbf.request.duplicate-in-days:7}") int duplicateInDays) {
        this.localService = localService;
        this.tpcConfirmInfoRepository = tpcConfirmInfoRepository;
        this.tpcConfirmInfoCrudRepository = tpcConfirmInfoCrudRepository;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
        this.confirmInfoReceiveCrudRepository = confirmInfoReceiveCrudRepository;
        this.duplicateInDays = duplicateInDays;
    }

    @Scheduled(cron = "${tpc.aggregate.cron:0/20 * * * * *}")
    public void aggregateTpcConfirmInfo() {
        List<String> tpcUserIds = localService.getTpcUserIds();
        if (tpcUserIds == null || tpcUserIds.isEmpty()) {
            return;
        }
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdDate"));
        Page<String> confirmInfoIds =
                confirmInfoCrudRepository.findAggregateConfirmInfoBy(tpcUserIds, pageable);
        while (!confirmInfoIds.isEmpty()) {
            Iterable<ConfirmInfo> confirmInfos = confirmInfoCrudRepository.findIncludeParamsByIdIn(confirmInfoIds.stream().collect(Collectors.toList()));
            for (ConfirmInfo confirmInfo : confirmInfos) {
                initTpcConfirmInfo(confirmInfo);
            }
            System.gc();
            if (!confirmInfoIds.hasNext()) break;
            confirmInfoIds = confirmInfoCrudRepository
                    .findAggregateConfirmInfoBy(tpcUserIds, confirmInfoIds.nextPageable());
        }
    }

    @Scheduled(cron = "${tpc.aggregate.cron:0/20 * * * * *}")
    public void aggregateTpcConfirmInfoReceive() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdDate"));
        Page<String> confirmInfoIds =
                confirmInfoCrudRepository.findInitedConfirmInfoIdsByAggregateStatus(69, pageable);
        while (!confirmInfoIds.isEmpty()) {
            Iterable<ConfirmInfo> confirmInfos = confirmInfoCrudRepository
                    .findIncludeParamsAndConfirmInfoReceivesByIdIn(confirmInfoIds.stream().collect(Collectors.toList()));
            for (ConfirmInfo confirmInfo : confirmInfos) {
                if (confirmInfo.getConfirmInfoReceives() == null ||
                        confirmInfo.getConfirmInfoReceives().isEmpty()) {
                    updateTpcConfirmInfo(confirmInfo, null);
                } else {
                    confirmInfo.getConfirmInfoReceives().forEach((k, cr) -> {
                        updateTpcConfirmInfo(confirmInfo, cr);
                    });
                }
            }
            System.gc();
            if (!confirmInfoIds.hasNext()) break;
            confirmInfoIds = confirmInfoCrudRepository
                    .findInitedConfirmInfoIdsByAggregateStatus(69, confirmInfoIds.nextPageable());
        }
    }


    private void updateTpcConfirmInfo(ConfirmInfo confirmInfo, ConfirmInfoReceive confirmInfoReceive) {
        TpcConfirmInfo tpcConfirmInfo = initTpcConfirmInfo(confirmInfo);

        if (confirmInfo.getServiceType() == ServiceType.FC_REF_PHONE
                || confirmInfo.getServiceType() == ServiceType.FC_ADV_REF_PHONE
                || confirmInfo.getServiceType() == ServiceType.FC_TPC_REF_PHONE) {
            tpcConfirmInfo.setCallMessageStatus(confirmInfo.getMessageStatus());
            if (confirmInfoReceive != null && !confirmInfoReceive.getAggregated()) {
                confirmInfoReceive.setAggregated(true);
                if (confirmInfo.getServiceType() == ServiceType.FC_REF_PHONE) {
                    List<CallBase> res = new ArrayList<>();
                    MfsCallBasic mfsCallBasic = JsonUtils.toObject(confirmInfoReceive.getData(), MfsCallBasic.class);
                    if (mfsCallBasic == null || mfsCallBasic.getStatusReport() == null) {
                        Map body = JsonUtils.toObject(confirmInfoReceive.getData(), Map.class);
                        try {
                            Map response = JsonUtils.convert(body.get("callReference"), Map.class);
                            mfsCallBasic = JsonUtils.convert(response.get("data"), MfsCallBasic.class);
                        } catch (Exception ignored) {
                        }
                    }
                    if (mfsCallBasic != null && mfsCallBasic.getStatusReport() != null) {
                        MfsCallStatus callStatus = mfsCallBasic.getStatusReport();
                        res.add(new RefPhone1(confirmInfo.getParamValue(Param.REF_PHONE_1), callStatus.getRefPhone1Status()));
                        res.add(new RefPhone2(confirmInfo.getParamValue(Param.REF_PHONE_2), callStatus.getRefPhone2Status()));
                    }
                    updateCallResults(tpcConfirmInfo, res);
                } else {
                    CallReference callReference = new CallReference(confirmInfoReceive);
                    updateCallResults(tpcConfirmInfo, callReference.getReferenceReports());
                }
            }
        } else if (confirmInfo.getServiceType() == ServiceType.FC_IMEI
                || confirmInfo.getServiceType() == ServiceType.FC_BS_IMEI_01
                || confirmInfo.getServiceType() == ServiceType.FC_BS_IMEI_02) {
            tpcConfirmInfo.setImeiMessageStatus(confirmInfo.getMessageStatus());
            if (confirmInfoReceive != null && !confirmInfoReceive.getAggregated()) {
                ImeiReference imeiReference = new ImeiReference(confirmInfoReceive);
                if (imeiReference.getResult() != null) {
                    tpcConfirmInfo.setImeiResult(Result.of(imeiReference.getResult()));
                }
                confirmInfoReceive.setAggregated(true);
            }

        }
        confirmInfo.setAggregateStatus(confirmInfo.getMessageStatus() == MessageStatus.CUSTOMER_TIMEOUT ? 2 : 1);
        confirmInfoCrudRepository.save(confirmInfo);
        tpcConfirmInfoCrudRepository.save(tpcConfirmInfo);
    }

    private TpcConfirmInfo updateCallResults(TpcConfirmInfo tpcConfirmInfo, List<CallBase> callBases) {
        if (callBases != null) {
            for (CallBase callBase : callBases) {
                if (callBase instanceof RefPhone1) {
                    RefPhone1 refPhone1 = (RefPhone1) callBase;
                    if (refPhone1.getResult() != null) {
                        tpcConfirmInfo.setRefPhone1Result(Result.of(refPhone1.getResult()));
                    }
                } else if (callBase instanceof RefPhone2) {
                    RefPhone2 refPhone2 = (RefPhone2) callBase;
                    if (refPhone2.getResult() != null) {
                        tpcConfirmInfo.setRefPhone2Result(Result.of(refPhone2.getResult()));
                    }
                }
            }
        }
        return tpcConfirmInfo;
    }

    private TpcConfirmInfo initTpcConfirmInfo(ConfirmInfo confirmInfo) {
        Date duplicateBefore = DateUtils.addDay(confirmInfo.getCreatedDate(), -duplicateInDays);
        TpcConfirmInfo tpcConfirmInfo = tpcConfirmInfoRepository
                .getAggregateTpcConfirmInfo(confirmInfo.getPhoneNumber(), confirmInfo.getCreatedDate(), duplicateBefore);

        if (tpcConfirmInfo == null) {
            tpcConfirmInfo = new TpcConfirmInfo();
        }

        tpcConfirmInfo.setCheckDate(confirmInfo.getCreatedDate());
        tpcConfirmInfo.setCheckDateTime(confirmInfo.getCreatedDate());
        tpcConfirmInfo.setPhoneNumber(confirmInfo.getPhoneNumber());
        tpcConfirmInfo.setCreatedBy(confirmInfo.getCreatedBy());

        if (confirmInfo.getServiceType() == ServiceType.FC_REF_PHONE
                || confirmInfo.getServiceType() == ServiceType.FC_ADV_REF_PHONE
                || confirmInfo.getServiceType() == ServiceType.FC_TPC_REF_PHONE) {
            tpcConfirmInfo.setRefPhone1(confirmInfo.getParamValue(Param.REF_PHONE_1));
            tpcConfirmInfo.setRefPhone2(confirmInfo.getParamValue(Param.REF_PHONE_2));
            tpcConfirmInfo.setCheckCallDate(confirmInfo.getCreatedDate());
            tpcConfirmInfo.setCheckCallDateTime(confirmInfo.getCreatedDate());
            if (confirmInfo.getMessageStatus() != null) {
                tpcConfirmInfo.setCallMessageStatus(confirmInfo.getMessageStatus());
            }

        } else if (confirmInfo.getServiceType() == ServiceType.FC_IMEI
                || confirmInfo.getServiceType() == ServiceType.FC_BS_IMEI_01
                || confirmInfo.getServiceType() == ServiceType.FC_BS_IMEI_02) {
            tpcConfirmInfo.setCheckImeiDate(confirmInfo.getCreatedDate());
            tpcConfirmInfo.setCheckImeiDateTime(confirmInfo.getCreatedDate());
            if (confirmInfo.getMessageStatus() != null) {
                tpcConfirmInfo.setImeiMessageStatus(confirmInfo.getMessageStatus());
            }
        }

        // mark confirm_info inited tpc_confirm_info
        confirmInfo.setAggregateStatus(0);
        confirmInfoCrudRepository.save(confirmInfo);
        return tpcConfirmInfoCrudRepository.save(tpcConfirmInfo);
    }
}
