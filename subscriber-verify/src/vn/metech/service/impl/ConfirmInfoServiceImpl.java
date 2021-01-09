package vn.metech.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.metech.common.RecordStatus;
import vn.metech.common.ServiceType;
import vn.metech.common.ValueKey;
import vn.metech.dto.UserInfo;
import vn.metech.dto.request.RequestBase;
import vn.metech.dto.request.monitor.ConfirmRequest;
import vn.metech.dto.request.monitor.FilterRequest;
import vn.metech.dto.response.*;
import vn.metech.entity.ApiReceiveLog;
import vn.metech.entity.ConfirmInfo;
import vn.metech.exception.aio.*;
import vn.metech.repository.ConfirmInfoRepository;
import vn.metech.repository.jpa.ApiReceiveLogCrudRepository;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;
import vn.metech.service.BalanceService;
import vn.metech.service.IConfirmInfoService;
import vn.metech.service.LocalService;
import vn.metech.service.base.ServiceImpl;
import vn.metech.util.DateUtils;
import vn.metech.repository.jpa.StatusInfoCrudRepository;
import vn.metech.dto.response.StatusResponse;
import vn.metech.repository.jpa.PartnerCrudRepository;
import vn.metech.repository.ISubPartnerRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfirmInfoServiceImpl extends ServiceImpl<ConfirmInfo> implements IConfirmInfoService {

    private final boolean balanceFeatureFlag;


    private final LocalService localService;
    private final BalanceService balanceService;
    private final ConfirmInfoRepository confirmInfoRepository;
    private final ConfirmInfoCrudRepository confirmInfoCrudRepository;
    private final ApiReceiveLogCrudRepository apiReceiveLogCrudRepository;
    private final StatusInfoCrudRepository statusInfoCrudRepository;
    private final PartnerCrudRepository partnerCrudRepository;
    private final ISubPartnerRepository subPartnerRepository;

    public ConfirmInfoServiceImpl(
            LocalService localService, BalanceService balanceService,
            ConfirmInfoRepository confirmInfoRepository,
            ConfirmInfoCrudRepository confirmInfoCrudRepository,
            ApiReceiveLogCrudRepository apiReceiveLogCrudRepository,
            StatusInfoCrudRepository statusInfoCrudRepository,
            PartnerCrudRepository partnerCrudRepository,
            ISubPartnerRepository subPartnerRepository,
            @Value(ValueKey.FEATURE_BALANCE_FLAG) boolean balanceFeatureFlag) {
        this.localService = localService;
        this.balanceService = balanceService;
        this.confirmInfoRepository = confirmInfoRepository;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
        this.apiReceiveLogCrudRepository = apiReceiveLogCrudRepository;
        this.balanceFeatureFlag = balanceFeatureFlag;
        this.statusInfoCrudRepository = statusInfoCrudRepository;
        this.partnerCrudRepository = partnerCrudRepository;
        this.subPartnerRepository = subPartnerRepository;
    }

    @Override
    public ActionResult<? extends HashResponseBase> receiveConfirmInfoRequest(
            RequestBase request, String userId, boolean aio) throws ParamInvalidException,
            BalanceNotEnoughException, RequestDuplicateException, RequestTooManyException, SystemBusyException {
        apiReceiveLogCrudRepository.save(new ApiReceiveLog(request, userId, aio));
        long counter = confirmInfoRepository.countConfirmInfoByRequest(request.id());
        if (counter > 0) {
            throw new RequestDuplicateException(request, request.id());
        }

        UserInfo userInfo = localService.getUserInfoAio(userId);
        if (userInfo == null) {
            throw new SystemBusyException(request, "Xảy ra lỗi trong quá trình request");
        }

        ConfirmInfo confirmInfo = validateRequest(request, userInfo);
//        confirmInfoRepository.save(confirmInfo);
        confirmInfoCrudRepository.save(confirmInfo).getConfirmInfo();
        return ActionResult.ok(
                request.getCommand(),
                request.getServiceType(),
                confirmInfo.getRequestId(), null
        );
    }


    private ConfirmInfo validateRequest(RequestBase request, UserInfo userInfo)
            throws ParamInvalidException, BalanceNotEnoughException, RequestTooManyException {
        ConfirmInfo confirmInfo = new ConfirmInfo(request, userInfo.getUserId());
        confirmInfo.setAccount(userInfo.getAccount());
        confirmInfo.setSecureKey(userInfo.getSecureKey());
        confirmInfo.setPartnerId(userInfo.getPartnerId());
        confirmInfo.setPartnerName(userInfo.getPartnerName());
        confirmInfo.setSubPartnerId(userInfo.getSubPartnerId());
        confirmInfo.setSubPartnerName(userInfo.getSubPartnerName());

//        if (hashSecurityFeatureFlag) {
//            if (!request.isHashMatch(requestHashKey)) {
//                throw new RequestInvalidException(request, confirmInfo.getRequestId());
//            }
//        }

        validateRequestFrequency(request, userInfo);

        if (!request.getServiceType().isRecall()) {
            if (isBalanceEnoughToRequest(request, confirmInfo.getRequestId(), userInfo.getUserId())) {
                confirmInfo.setCustomerCode(userInfo.getDefaultCustomerCode());

                return confirmInfo;
            }
        }

        return confirmInfo;
    }


    private void validateRequestFrequency(RequestBase request, UserInfo userInfo) throws RequestTooManyException, ParamInvalidException {
        ConfirmInfo confirmInfo = new ConfirmInfo(request, userInfo.getUserId());
        long requestCount = confirmInfoCrudRepository
                .countByPhoneNumberAndServiceType(confirmInfo.getPhoneNumber(), confirmInfo.getServiceType());
        if (requestCount > 0) {
            ConfirmInfo existConfirmInfo = confirmInfoCrudRepository
                    .findFirstByPhoneNumberAndServiceTypeOrderByCreatedDateDesc(
                            confirmInfo.getPhoneNumber(),
                            confirmInfo.getServiceType()
                    );

            Date now = new Date();
            if (existConfirmInfo.getRecordStatus() == RecordStatus.READY) {
                Date validDate = DateUtils.addDay(existConfirmInfo.getCreatedDate(), 30);
                if (now.compareTo(validDate) < 1) {
                    throw new RequestTooManyException(
                            request,
                            "Yêu cầu này đã kiểm tra trong vòng 30 ngày, vui lòng thử lại."
                    );
                }
            } else {
                Date validDate = DateUtils.addDay(existConfirmInfo.getCreatedDate(), 2);
                if (now.compareTo(validDate) < 1) {
                    throw new RequestTooManyException(
                            request,
                            "Yêu cầu này đã kiểm tra trong vòng 48h, vui lòng thử lại."
                    );

                }
            }
        }
    }

    private boolean isBalanceEnoughToRequest(
            RequestBase request, String requestId, String userId) throws BalanceNotEnoughException {
        if (balanceFeatureFlag) {
            if (!balanceService.checkBalance(request.getServiceType().verifyService(), userId, requestId)) {
                throw new BalanceNotEnoughException(request, requestId);
            }
        }
        return true;
    }

    @Override
    public PageResponse<MonitorResponse> getFilRequest(RequestBase req, ConfirmRequest confirmRequest, String userId) throws RequestDuplicateException {

        UserInfo userInfo = localService.getUserInfoAio(userId);
        if (!userInfo.isAdmin()) {
            throw new RequestDuplicateException(req, "Account không tồn tại hoặc không được phép truy cập API");
        }
        PageResponse<ConfirmInfo> confirmInfos = confirmInfoRepository.getConfirmInfoBy(confirmRequest);
     //   List<ConfirmInfo> confirmInfoList = confirmInfoRepository.getConfirmInfoByBy(confirmRequest, confirmRequest);

//        return new PageResponse<>(confirmInfos.getTotal(), MonitorResponse.of(confirmInfoList), confirmRequest);
        return new PageResponse<>(confirmInfos.getTotal(), MonitorResponse.of(confirmInfos.getData()), confirmRequest);
    }

    @Override
    public PageResponse<ConfirmInfoResponse> getFilter(RequestBase req, FilterRequest filter, String userId) throws RequestDuplicateException {
        UserInfo userInfo = localService.getUserInfoAio(userId);

        if (userInfo == null || !userInfo.isAdmin()) {
            throw new RequestDuplicateException(req, "Account không tồn tại hoặc không được phép truy cập API");
        }
        PageResponse<ConfirmInfoResponse> confirm = confirmInfoRepository.getReportConfirmInfoResponse(filter);
//        List<ConfirmInfoResponse> confirmList = confirmInfoRepository.getFilterRequestBy(filter, filter);

//        return new PageResponse<>(confirm.getTotal(), confirm.getData(), filter);
        return confirm;
    }

    @Override
    public List<StatusResponse> getAllStatus() {
        return statusInfoCrudRepository.getListStatus();
    }

    @Override
    public List<String> getListServices() {
        return Arrays.stream(ServiceType.values()).map(x->x.name()).collect(Collectors.toList());
//        return confirmInfoCrudRepository.getConfirmInfoServicesByService();
    }

    @Override
    public List<PartnerResponse> getListPartner() {
        return partnerCrudRepository.getListPartner();
    }

    @Override
    public List<SubPartnerResponse> getListSubPartner(String partnerId) {
        return subPartnerRepository.getConfirmInfoSubPartnerIdBy(partnerId);
    }

}
