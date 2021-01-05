package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import vn.metech.common.Param;
import vn.metech.dto.request.RequestBase;
import vn.metech.dto.response.ActionResult;
import vn.metech.dto.response.AdvanceReport;
import vn.metech.dto.response.HashResponseBase;
import vn.metech.dto.response.data.*;
import vn.metech.dto.response.fc02.FC0201Response;
import vn.metech.dto.response.fc02.FC0202Response;
import vn.metech.dto.response.fc03.FC03Response;
import vn.metech.dto.response.fc03.KYC03Response;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.exception.aio.ParamInvalidException;
import vn.metech.exception.aio.RequestNotFoundException;
import vn.metech.repository.IConfirmInfoReceiveRepository;
import vn.metech.service.GMapService;
import vn.metech.service.IConfirmInfoReceiveService;
import vn.metech.service.LocalService;
import vn.metech.service.base.ServiceImpl;
import vn.metech.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfirmInfoReceiveServiceImpl
        extends ServiceImpl<ConfirmInfoReceive> implements IConfirmInfoReceiveService {

    private final GMapService gMapService;
    private final LocalService localService;
    private final IConfirmInfoReceiveRepository confirmInfoReceiveRepository;

    public ConfirmInfoReceiveServiceImpl(
            GMapService gMapService,
            LocalService localService,
            IConfirmInfoReceiveRepository confirmInfoReceiveRepository) {
        this.gMapService = gMapService;
        this.localService = localService;
        this.confirmInfoReceiveRepository = confirmInfoReceiveRepository;
    }

    @Override
    public ActionResult<? extends HashResponseBase> findResponse(RequestBase request, String userId)
            throws ParamInvalidException, RequestNotFoundException {
        String requestId = request.id();
        if (!request.isValid()) throw new ParamInvalidException(request, requestId);

        List<String> userIds = localService.getRecordsUserIds(userId);
        if (userIds == null || userIds.isEmpty()) {
            return ActionResult.ok(request.getCommand(), request.getServiceType(), requestId, null);
        }

        return ActionResult
                .ok(request.getCommand(), request.getServiceType(), requestId, getResponseOf(request, userIds));

//        return ActionResult
//                .ok(request.getCommand(), request.getServiceType(), requestId, getResponseOf(request, null));
    }

    @Override
    public HashResponseBase getResponseOf(RequestBase request, List<String> userIds) throws RequestNotFoundException {
        boolean isRecall = request.getServiceType().isRecall();
        ConfirmInfoReceive confirmInfoReceive;
        if (isRecall) {
            confirmInfoReceive = confirmInfoReceiveRepository.findByRequestId(request.id(), userIds);
        } else {
            String phoneNumber = String.valueOf(request.params().get(Param.PHONE_NUMBER));
            confirmInfoReceive = confirmInfoReceiveRepository
                    .findByPhoneNumberAndServiceType(phoneNumber, request.getServiceType(), userIds);
        }

        if (confirmInfoReceive == null
                || confirmInfoReceive.getConfirmInfo() == null
                || confirmInfoReceive.getConfirmInfo().getParams() == null) {
            throw new RequestNotFoundException(request, request.id());
        }

        switch (confirmInfoReceive.getServiceType()) {
            case FC_KYC02_VPB:
                return getKyc02ResponseOf(confirmInfoReceive);
            case FC_ADV_CAC:
                return getLocationAdvanceResponseOf(confirmInfoReceive);
            case FC_BS_CAC_01:
                return getFC03ResponseOf(confirmInfoReceive);
            case FC_KYC_03:
                return getKYC03ResponseOf(confirmInfoReceive);
            case FC_IMEI:
                return getImeiResponseOf(confirmInfoReceive);
            case FC_BS_IMEI_01:
                return getFC0201ResponseOf(confirmInfoReceive);
            case FC_BS_IMEI_02:
                return getFC0202ResponseOf(confirmInfoReceive);
            case FC_ID:
                return getIdResponseOf(confirmInfoReceive);
            case FC_REF_PHONE:
                return getRefPhoneResponseOf(confirmInfoReceive);
            case FC_ADV_REF_PHONE:
            case FC_TPC_REF_PHONE:
                return getRefPhoneAdvanceResponseOf(confirmInfoReceive);
            case FC_ADV_CB01:
                return getCombo01AdvanceResponseOf(confirmInfoReceive);
            case FC_ADV_CB02:
                return getCombo02AdvanceResponseOf(confirmInfoReceive);
            case FC_ADV_CB03:
                return getCombo03AdvanceResponseOf(confirmInfoReceive);
            case FC_BS_CB04:
                return getCombo04BasicResponseOf(confirmInfoReceive);
//			case FC_BS_CB05:
//				return getCombo05BasicResponseOf(calledConfirmInfo);

        }

        return null;
    }

//	private AdvanceReport<Combo05Basic> getCombo05BasicResponseOf(ConfirmInfo confirmInfo) {
//		AdvanceReport<Combo05Basic> advanceReport = new AdvanceReport<>(confirmInfo);
//		advanceReport.setReports(new Combo04Basic(
//						getLocationAdvanceResponseOf(confirmInfo).getReports(),
//						getRefPhoneAdvanceResponseOf(confirmInfo).getReports()
//		));
//
//		return advanceReport;
//	}

    private AdvanceReport<DwhKyc02Data> getKyc02ResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<DwhKyc02Data> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        DwhKyc02Data kyc02Data = JsonUtils.convert(confirmInfoReceive.getData(), DwhKyc02Data.class);
        advanceReport.setReports(kyc02Data);

        return advanceReport;

    }

    private AdvanceReport<Combo04Basic> getCombo04BasicResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<Combo04Basic> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new Combo04Basic(
                getRefPhoneResponseOf(confirmInfoReceive).getReports(),
                getImeiResponseOf(confirmInfoReceive).getReports().getResult()
        ));

        return advanceReport;
    }

    private AdvanceReport<Combo01Advance> getCombo01AdvanceResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<Combo01Advance> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new Combo01Advance(
                getLocationAdvanceResponseOf(confirmInfoReceive).getReports(),
                getRefPhoneAdvanceResponseOf(confirmInfoReceive).getReports()
        ));

        return advanceReport;
    }

    private AdvanceReport<Combo02Advance> getCombo02AdvanceResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<Combo02Advance> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new Combo02Advance(
                getLocationAdvanceResponseOf(confirmInfoReceive).getReports(),
                getRefPhoneAdvanceResponseOf(confirmInfoReceive).getReports(),
                getImeiResponseOf(confirmInfoReceive).getReports().getResult()
        ));

        return advanceReport;
    }

    private AdvanceReport<Combo03Advance> getCombo03AdvanceResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<Combo03Advance> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new Combo03Advance(
                getLocationAdvanceResponseOf(confirmInfoReceive).getReports(),
                getRefPhoneAdvanceResponseOf(confirmInfoReceive).getReports(),
                getImeiResponseOf(confirmInfoReceive).getReports().getResult(),
                confirmInfoReceive.getConfirmInfo().getParamValue(Param.ID_NUMBER),
                getIdResponseOf(confirmInfoReceive).getReports().getResult()
        ));

        return advanceReport;
    }

    private AdvanceReport<LocationAdvance> getLocationAdvanceResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<LocationAdvance> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new LocationAdvance(confirmInfoReceive, gMapService));

        return advanceReport;
    }

    private AdvanceReport<FC03Response> getFC03ResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<FC03Response> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new FC03Response(confirmInfoReceive));

        return advanceReport;
    }

    private AdvanceReport<KYC03Response> getKYC03ResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<KYC03Response> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new KYC03Response(confirmInfoReceive));

        return advanceReport;
    }

    private AdvanceReport<ImeiReference> getImeiResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<ImeiReference> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new ImeiReference(confirmInfoReceive));

        return advanceReport;
    }

    private AdvanceReport<FC0201Response> getFC0201ResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<FC0201Response> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new FC0201Response(confirmInfoReceive));

        return advanceReport;
    }

    private AdvanceReport<FC0202Response> getFC0202ResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<FC0202Response> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new FC0202Response(confirmInfoReceive));

        return advanceReport;
    }

    private AdvanceReport<IdReference> getIdResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<IdReference> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new IdReference(confirmInfoReceive));

        return advanceReport;
    }

    private AdvanceReport<CallReference> getRefPhoneAdvanceResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        AdvanceReport<CallReference> advanceReport = new AdvanceReport<>(confirmInfoReceive.getConfirmInfo());
        advanceReport.setReports(new CallReference(confirmInfoReceive));

        return advanceReport;
    }


    private AdvanceReport<List<CallBase>> getRefPhoneResponseOf(ConfirmInfoReceive confirmInfoReceive) {
        ConfirmInfo confirmInfo = confirmInfoReceive.getConfirmInfo();
        AdvanceReport<List<CallBase>> advanceReport = new AdvanceReport<>(confirmInfo);
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

        advanceReport.setReports(res);
        return advanceReport;
    }

}
