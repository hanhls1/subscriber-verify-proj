package vn.metech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.metech.common.*;
import vn.metech.dto.response.DwhGenericeResponse;
import vn.metech.entity.ApiRequestLog;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.util.JsonUtils;
import vn.metech.util.RestUtils;
import vn.metech.util.RestUtils.RestResponse;
import vn.metech.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class DwhRequestService {

    private final String baseUrl;

//    private final ConfirmInfoRepository confirmInfoRepository;
    private final CrudRepository<ConfirmInfoReceive, String> confirmInfoReceiveRepository;
    private final CrudRepository<ApiRequestLog, String> apiRequestLogRepository;
    private final CrudRepository<ConfirmInfo, String> confirmInfoRepository;

    public DwhRequestService(
            @Value(ValueKey.PARTNER_DWH_REQUEST_URL) String baseUrl,
//            ConfirmInfoRepository confirmInfoRepository,
            CrudRepository<ConfirmInfoReceive, String> confirmInfoReceiveRepository,
            CrudRepository<ApiRequestLog, String> apiRequestLogRepository,
            CrudRepository<ConfirmInfo, String> confirmInfoRepository) {
        this.baseUrl = baseUrl;
        this.confirmInfoRepository = confirmInfoRepository;
        this.confirmInfoReceiveRepository = confirmInfoReceiveRepository;
        this.apiRequestLogRepository = apiRequestLogRepository;
//        this.confirmInfoRepository = confirmInfoCrudRepository;
    }

    public void sendRequest(ConfirmInfo confirmInfo) {
        if (confirmInfo.getServiceType() == ServiceType.FC_KYC02_VPB) {
            sendKyc02Request(confirmInfo);
        }
    }

    public void sendKyc02Request(ConfirmInfo confirmInfo) {
        send("/usermodel/info", confirmInfo);
//		send("/usermodel/score", confirmInfo);
    }

    private void send(String absolutePath, ConfirmInfo confirmInfo) {
        Assert.notNull(absolutePath, "absolutePath required");
        String qRequestId = StringUtils._3tId();
        Map<String, Object> params = new HashMap<>();
        params.put("nationalId", confirmInfo.getParamValue(Param.ID_NUMBER));
        params.put("requestId", qRequestId);
        RestResponse<DwhGenericeResponse> res = RestUtils.get(baseUrl + absolutePath, params, DwhGenericeResponse.class);
        ApiRequestLog apiRequestLog = new ApiRequestLog(res, confirmInfo);
        if (res.getBody() != null) {
            apiRequestLog.setResponseId(qRequestId);
            DwhStatus dwhStatus = res.getBody().getDwhStatus();
            if (dwhStatus == DwhStatus.SUCCESS) {
                confirmInfo.setqRequestId(qRequestId);
                confirmInfo.setRecordStatus(RecordStatus.READY);
                ConfirmInfoReceive confirmInfoReceive = new ConfirmInfoReceive(confirmInfo);
                confirmInfoReceive.setData(JsonUtils.toJson(res.getBody().getData()));
                confirmInfoReceiveRepository.save(confirmInfoReceive);
            } else if (dwhStatus == DwhStatus.REQUEST_ERROR) {
                confirmInfo.setRecordStatus(RecordStatus.INVALID);
            } else {
                confirmInfo.setRecordStatus(RecordStatus.NO_FETCH_DATA);
            }
        }
        confirmInfo.setNumberOfResults(confirmInfo.getNumberOfResults() + 1);
        confirmInfo.setqRequestId(apiRequestLog.getRequestId());
        apiRequestLogRepository.save(apiRequestLog);
        confirmInfoRepository.save(confirmInfo);

    }

}
