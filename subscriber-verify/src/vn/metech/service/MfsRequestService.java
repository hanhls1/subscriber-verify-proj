package vn.metech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.metech.common.*;
import vn.metech.dto.MfsLocation;
import vn.metech.dto.RequestWithKey;
import vn.metech.dto.gplace.GPlaceLocation;
import vn.metech.dto.request.*;
import vn.metech.dto.response.MfsGenericResponse;
import vn.metech.dto.response.data.MfsSuccessRequestList;
import vn.metech.entity.ApiRequestLog;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.repository.ConfirmInfoRepository;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.RestUtils;
import vn.metech.util.RestUtils.RestResponse;
import vn.metech.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MfsRequestService {

    private final String baseUrl;

    private final GMapService gMapService;
    private final ConfirmInfoRepository confirmInfoRepository;
    private final CrudRepository<ConfirmInfoReceive, String> confirmInfoReceiveRepository;
    private final CrudRepository<ApiRequestLog, String> apiRequestLogRepository;
    private final ConfirmInfoCrudRepository confirmInfoCrudRepository;

    public MfsRequestService(
            GMapService gMapService,
            @Value(ValueKey.PARTNER_MFS_REQUEST_URL) String baseUrl,
            ConfirmInfoRepository confirmInfoRepository,
            ConfirmInfoCrudRepository confirmInfoCrudRepository,
            CrudRepository<ConfirmInfoReceive, String> confirmInfoReceiveRepository,
            CrudRepository<ApiRequestLog, String> apiRequestLogRepository) {
        this.baseUrl = baseUrl;
        this.gMapService = gMapService;
        this.confirmInfoRepository = confirmInfoRepository;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
        this.confirmInfoReceiveRepository = confirmInfoReceiveRepository;
        this.apiRequestLogRepository = apiRequestLogRepository;
    }

    public void sendRequest(ConfirmInfo confirmInfo) {
        switch (confirmInfo.getServiceType()) {
            case FC_IMEI:
            case FC_BS_IMEI_02:
            case FC_BS_IMEI_01:
                sendImeiRequest(confirmInfo);
                break;
            case FC_ADV_CAC:
            case FC_BS_CAC_01:
            case FC_KYC_03:
            case FC_TPC_ADV_CAC:
            case FC_TPC_KYC_02:
                sendLocationAdvanceRequest(confirmInfo);
                break;
            case FC_ID:
            case FC_TPC_ID:
                sendIdRequest(confirmInfo);
                break;
            case FC_REF_PHONE:
            case FC_ADV_REF_PHONE:
            case FC_TPC_REF_PHONE:
                sendRefPhoneRequest(confirmInfo);
                break;
            case FC_ADV_CB01:
                sendCombo01AdvanceRequest(confirmInfo);
                break;
            case FC_ADV_CB02:
                sendCombo02AdvanceRequest(confirmInfo);
                break;
            case FC_ADV_CB03:
                sendCombo03AdvanceRequest(confirmInfo);
                break;  //
            case FC_BS_CB04:
                sendCombo04BasicRequest(confirmInfo);
                break;
        }
    }

    public void sendImeiRequest(ConfirmInfo confirmInfo) {
        send("/api/v2/adreference", confirmInfo, new MfsImeiRequest(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey()));
    }

    public void sendIdRequest(ConfirmInfo confirmInfo) {
        send("/api/v2/idreference", confirmInfo, new MfsIdRequest(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey()));
    }

    public void sendRefPhoneRequest(ConfirmInfo confirmInfo) {
        String path = null;
        if (confirmInfo.getServiceType() == ServiceType.FC_REF_PHONE) {
            path = "/api/v2/callreference_basic";
        } else if (confirmInfo.getServiceType() == ServiceType.FC_ADV_REF_PHONE
                || confirmInfo.getServiceType() == ServiceType.FC_TPC_REF_PHONE) {
            path = "/api/v2/callreference_advance";
        }
        send(path, confirmInfo, new MfsCallRequest(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey()));
    }

    public void sendCombo01AdvanceRequest(ConfirmInfo confirmInfo) {
        sendWithGMapPlacesConvertedAsync(
                "/api/v2/comboRef1_advance", confirmInfo, new MfsCombo01Request(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey()));
    }

    public void sendCombo02AdvanceRequest(ConfirmInfo confirmInfo) {
        sendWithGMapPlacesConvertedAsync(
                "/api/v2/comboRef2_advance", confirmInfo, new MfsCombo02Request(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey()));
    }

    public void sendCombo03AdvanceRequest(ConfirmInfo confirmInfo) {
        sendWithGMapPlacesConvertedAsync(
                "/api/v2/comboRef3_advance", confirmInfo, new MfsCombo03Request(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey()));
    }

    public void sendCombo04BasicRequest(ConfirmInfo confirmInfo) {
        sendRefPhoneRequest(confirmInfo);
        sendImeiRequest(confirmInfo);
    }

    public void fetchAnswers() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "createdDate"));
        Page<RequestWithKey> requestWithKeys = confirmInfoCrudRepository
                .findRequestWithKeyBy(Collections.singletonList(RecordStatus.REQUESTING), date, pageable);

        while (!requestWithKeys.isEmpty()) {
            for (RequestWithKey requestWithKey : requestWithKeys) {
                List<String> requestIds = fetchSuccessRequestIds(requestWithKey);
                if (requestIds == null || requestIds.isEmpty()) {
                    return;
                }
                for (String requestId : requestIds) {
                    fetchAnswer(requestWithKey, requestId);
                }
            }
            if (!requestWithKeys.hasNext()) break;

            requestWithKeys = confirmInfoCrudRepository
                    .findRequestWithKeyBy(Collections.singletonList(RecordStatus.REQUESTING), date, requestWithKeys.nextPageable());
        }
    }

    public List<String> fetchSuccessRequestIds(RequestWithKey requestWithKey) {
        Assert.notNull(requestWithKey.getCustomerCode(), "customerCode required");
        Assert.notNull(requestWithKey.getAccount(), "account required");
        Assert.notNull(requestWithKey.getSecureKey(), "secureKey required");
        MfsGetSuccessRequest mfsGetSuccessRequest = new MfsGetSuccessRequest(
                null, requestWithKey.getCustomerCode(), requestWithKey.getAccount(), requestWithKey.getSecureKey()
        );
        RestResponse<MfsGenericResponse> res =
                RestUtils.post(baseUrl + "/api/v2/getSuccessRequest", null, mfsGetSuccessRequest, MfsGenericResponse.class);
        ApiRequestLog apiRequestLog = new ApiRequestLog(res, mfsGetSuccessRequest.getRequestId());
        apiRequestLogRepository.save(apiRequestLog);
        MfsGenericResponse body = res.getBody();
        if (body == null || body.getMfsStatus() != MfsStatus.SUCCESS) {
            return Collections.emptyList();
        }
        MfsSuccessRequestList successRequestList = JsonUtils.convert(body.getResponseData(), MfsSuccessRequestList.class);
        return successRequestList == null
                ? Collections.emptyList()
                : successRequestList.requestIds();
    }


    public void fetchAnswer(RequestWithKey requestWithKey, String qRequestId) {
        Assert.notNull(requestWithKey.getCustomerCode(), "customerCode required");
        Assert.notNull(requestWithKey.getAccount(), "account required");
        Assert.notNull(requestWithKey.getSecureKey(), "secureKey required");
        Assert.notNull(qRequestId, "qRequestId required");
        MfsGetSuccessRequest mfsGetSuccessRequest = new MfsGetSuccessRequest(
                qRequestId, requestWithKey.getCustomerCode(), requestWithKey.getAccount(), requestWithKey.getSecureKey()
        );
        RestResponse<MfsGenericResponse> res = RestUtils
                .post(baseUrl + "/api/v2/getResponseByRequestId", null, mfsGetSuccessRequest, MfsGenericResponse.class);
        ApiRequestLog apiRequestLog = new ApiRequestLog(res, mfsGetSuccessRequest.getRequestId());
        apiRequestLogRepository.save(apiRequestLog);
        MfsGenericResponse body = res.getBody();
        if (body == null || body.getMfsStatus() != MfsStatus.SUCCESS) {
            return;
        }

        Map response = JsonUtils.convert(body.getResponseData(), Map.class);
        Map data = response == null ? null : JsonUtils.convert(response.get("response"), Map.class);
        ConfirmInfo confirmInfo = confirmInfoRepository.getConfirmInfoByQRequest(qRequestId);
        if (confirmInfo != null) {
            confirmInfo.setLastFetch(new Date());
            confirmInfo.setFetchTimes(confirmInfo.getFetchTimes() + 1);
            confirmInfoCrudRepository.save(confirmInfo);
        }
        ConfirmInfoReceive confirmInfoReceive = new ConfirmInfoReceive(confirmInfo);
        if (response != null) {
            if (data != null) {
                MfsStatus mfsStatus = MfsStatus.of((int) data.get("status"));
                System.out.println("RESPONSE GET : " + qRequestId + " mfsStatus: " + mfsStatus);
                if (mfsStatus == MfsStatus.SUCCESS) {
                    Object dataScope = data.get("data");
                    confirmInfoReceive.setData(JsonUtils.toJson(dataScope == null ? data : dataScope));
                    if (confirmInfo != null) {

                        //created
                        confirmInfo.setStatusCode(StatusCode.OK.value());
                        confirmInfo.setStatus(StatusCode.OK.description());

                        confirmInfo.setAggregateStatus(69);
                        confirmInfo.setRecordStatus(RecordStatus.READY);
                        confirmInfo.setMessageStatus(MessageStatus.ACCEPTED);
                        confirmInfoRepository.save(confirmInfo);
                    }
                } else if (mfsStatus == MfsStatus.MBF_RECORD_NOT_FOUND) {
                    if (confirmInfo != null) {

                        confirmInfo.setAggregateStatus(69);
                        confirmInfo.setRecordStatus(RecordStatus.NO_DATA);
                        confirmInfoRepository.save(confirmInfo);
                    }
                } else if (mfsStatus == MfsStatus.REQUEST_ID_NOT_FOUND) {
                    if (confirmInfo != null) {

                        confirmInfo.setAggregateStatus(69);
                        confirmInfo.setRecordStatus(RecordStatus.NO_FETCH_DATA);
                        confirmInfoRepository.save(confirmInfo);
                    }
                }
            } else {
                confirmInfoReceive.setData(JsonUtils.toJson(response));
                if (confirmInfo != null) {
                    confirmInfo.setAggregateStatus(69);
                    confirmInfo.setRecordStatus(RecordStatus.READY);
                    confirmInfo.setMessageStatus(MessageStatus.ACCEPTED);
                    confirmInfoRepository.save(confirmInfo);
                }
            }
        }
        confirmInfoReceiveRepository.save(confirmInfoReceive);
        if (confirmInfo != null) {
            apiRequestLog.setConfirmInfo(confirmInfo);
        }
        apiRequestLogRepository.save(apiRequestLog);

    }

    public void sendCombo05BasicRequest(ConfirmInfo confirmInfo) {
        sendCombo04BasicRequest(confirmInfo);
    }

    private <T extends MfsRequestBase> void send(String absolutePath, ConfirmInfo confirmInfo, T req) {
        try {
            Assert.notNull(absolutePath, "absolutePath required");
            RestResponse<MfsGenericResponse> res = RestUtils.post(baseUrl + absolutePath, null, req, MfsGenericResponse.class);
            ApiRequestLog apiRequestLog = new ApiRequestLog(res, confirmInfo);
            if (res.getBody() != null) {
                apiRequestLog.setResponseId(res.getBody().getResponseId());
                MfsStatus mfsStatus = res.getBody().getMfsStatus();
                if (mfsStatus == MfsStatus.SUCCESS) {

                    //created
                    confirmInfo.setStatusCode(StatusCode.OK.value());
                    confirmInfo.setStatus(StatusCode.OK.description());

                    confirmInfo.setRecordStatus(RecordStatus.REQUESTING);
                    confirmInfo.setMessageStatus(MessageStatus.REQUEST_SENT);
//                    confirmInfoRepository.save(confirmInfo);
                } else if (mfsStatus == MfsStatus.PARAM_INVALID || mfsStatus == MfsStatus.BALANCE_NOT_ENOUGH
                        || mfsStatus == MfsStatus.CUSTOMER_CODE_NOT_FOUND || mfsStatus == MfsStatus.CUSTOMER_CODE_INVALID) {
                    confirmInfo.setRecordStatus(RecordStatus.INVALID);

                    confirmInfo.setStatus(StatusCode.PARAM_INVALID.description());
                    confirmInfo.setStatusCode(StatusCode.PARAM_INVALID.value());
                } else {
                    confirmInfo.setRecordStatus(RecordStatus.ERROR);

                    confirmInfo.setStatus(StatusCode.UNDEFINDED.description());
                    confirmInfo.setStatusCode(StatusCode.UNDEFINDED.value());
                }
            }
            confirmInfo.setAggregateStatus(69);
            confirmInfo.setqRequestId(req.getRequestId());
            confirmInfo.setNumberOfResults(confirmInfo.getNumberOfResults());
            apiRequestLogRepository.save(apiRequestLog);
//            confirmInfoRepository.save(confirmInfo);
            confirmInfoCrudRepository.save(confirmInfo).getConfirmInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendLocationAdvanceRequest(ConfirmInfo confirmInfo) {
        MfsLocationRequest locationRequest = new MfsLocationRequest(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey());
        sendWithGMapPlacesConvertedAsync("/api/v2/location/checking", confirmInfo, locationRequest);
    }

    private <T extends MfsLocationRequest> void sendWithGMapPlacesConvertedAsync(
            String absolutePath, ConfirmInfo confirmInfo, T req) {
        String refAddrStr = confirmInfo.getParamValue(Param.REF_ADDRESS);
        AtomicInteger counterTask = StringUtils.isEmpty(refAddrStr) ? new AtomicInteger(2) : new AtomicInteger(3);
        new Thread(() -> {
            GPlaceLocation homeAddr = gMapService.getPlaceOf(confirmInfo.getParamValue(Param.HOME_ADDRESS));
            if (homeAddr != null) {
                req.setHomeAddr(new MfsLocation(homeAddr.getLat(), homeAddr.getLng()));
            }
            int task = counterTask.decrementAndGet();
            if (task == 0) {
                send(absolutePath, confirmInfo, req);
            }
        }).start();
        new Thread(() -> {
            GPlaceLocation workAddr = gMapService.getPlaceOf(confirmInfo.getParamValue(Param.WORK_ADDRESS));
            if (workAddr != null) {
                req.setWorkAddr(new MfsLocation(workAddr.getLat(), workAddr.getLng()));
            }
            int task = counterTask.decrementAndGet();
            if (task == 0) {
                send(absolutePath, confirmInfo, req);
            }
        }).start();

        if (!StringUtils.isEmpty(refAddrStr)) {
            new Thread(() -> {
                GPlaceLocation refAddr = gMapService.getPlaceOf(refAddrStr);
                if (refAddr != null) {
                    req.setRefAddr(new MfsLocation(refAddr.getLat(), refAddr.getLng()));
                }
                int task = counterTask.decrementAndGet();
                if (task == 0) {
                    send(absolutePath, confirmInfo, req);
                }
            }).start();
        }
    }
}
