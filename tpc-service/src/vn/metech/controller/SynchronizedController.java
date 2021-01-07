package vn.metech.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.metech.constant.AppService;
import vn.metech.dto.msg.TpcMessage;
import vn.metech.entity.TpcConfirm;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.service.TpcConfirmInfoService;
import vn.metech.service.TpcConfirmService;
import vn.metech.shared.ActionResult;
import vn.metech.util.StringUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("sync")
public class SynchronizedController {

    private TpcConfirmService tpcConfirmService;
    private TpcConfirmInfoService tpcConfirmInfoService;

    public SynchronizedController(
            TpcConfirmService tpcConfirmService,
            TpcConfirmInfoService tpcConfirmInfoService) {
        this.tpcConfirmService = tpcConfirmService;
        this.tpcConfirmInfoService = tpcConfirmInfoService;
    }

    @PostMapping("tpc-summary")
    public ActionResult summaryTPCResponse(@Valid @RequestBody TpcMessage tpcMessage) {
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

        return new ActionResult(true);
    }

}
