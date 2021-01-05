package vn.metech.service;

import org.springframework.stereotype.Service;
import vn.metech.common.Param;
import vn.metech.common.ServiceType;
import vn.metech.dto.request.fc02.FC02RequestFilter;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.fc02.FC02ListResponse;
import vn.metech.entity.ConfirmInfo;
import vn.metech.repository.ConfirmInfoRepository;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;
import vn.metech.repository.jpa.ConfirmInfoReceiveCrudRepository;
import vn.metech.util.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FC02SearchService {

    LocalService localService;
    ConfirmInfoRepository confirmInfoRepository;
    ConfirmInfoCrudRepository confirmInfoCrudRepository;
    ConfirmInfoReceiveCrudRepository confirmInfoReceiveCrudRepository;

    public FC02SearchService(
            LocalService localService,
            ConfirmInfoRepository confirmInfoRepository,
            ConfirmInfoCrudRepository confirmInfoCrudRepository,
            ConfirmInfoReceiveCrudRepository confirmInfoReceiveCrudRepository) {
        this.localService = localService;
        this.confirmInfoRepository = confirmInfoRepository;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
        this.confirmInfoReceiveCrudRepository = confirmInfoReceiveCrudRepository;
    }

    public PageResponse<FC02ListResponse> findResponses(FC02RequestFilter requestFilter, String userId) {
        List<String> userIds = localService.getRecordsUserIds(userId);
        if (userIds == null || userIds.isEmpty()) {
            return new PageResponse<>();
        }

        PageResponse<String> confirmInfoIds = confirmInfoRepository
                .getConfirmInfoIdsBy(
                        requestFilter.getPhoneNumber(),
                        ServiceType.FC_BS_IMEI_02,
                        requestFilter.getFromDate(),
                        requestFilter.getToDate(),
                        userIds,
//                        Collections.singletonList("0ac4b938-7014-45c5-a5e6-99722a28a351"),
                        requestFilter
                );


        Iterable<ConfirmInfo> confirmInfos = confirmInfoCrudRepository.findIncludeParamsAndConfirmInfoReceivesByIdIn(confirmInfoIds.getData());


        PageResponse<FC02ListResponse> res = new PageResponse<>();
        res.setTotal(confirmInfoIds.getTotal());
        res.setPageSize(confirmInfoIds.getPageSize());
        res.setCurrentPage(confirmInfoIds.getCurrentPage());

        Map<String, FC02ListResponse> responseMap = new HashMap<>();
        if (confirmInfos != null) {
            for (ConfirmInfo confirmInfo : confirmInfos) {
                if (confirmInfo.getConfirmInfoReceives() == null || confirmInfo.getConfirmInfoReceives().isEmpty()) {
                    FC02ListResponse mapValue = new FC02ListResponse();
                    mapValue.setCheckDate(DateUtils.parseDate(confirmInfo.getParamValue(Param.CHECK_DATE)));
                    mapValue.setCreatedDate(confirmInfo.getCreatedDate());
                    mapValue.setPhoneNumber(confirmInfo.getPhoneNumber());
                    responseMap.put(confirmInfo.getId(), mapValue);
                } else {
                    confirmInfo.getConfirmInfoReceives().forEach((k, cr) -> {
                        FC02ListResponse fc02Res = responseMap.get(confirmInfo.getId());
                        if (fc02Res == null) {
                            fc02Res = new FC02ListResponse(cr);
                            responseMap.put(confirmInfo.getId(), fc02Res);
                        } else if (fc02Res.getCreatedDateTime().getTime() < cr.getCreatedDate().getTime()) {
                            fc02Res = new FC02ListResponse(cr);
                            responseMap.put(confirmInfo.getId(), fc02Res);
                        }
                    });
                }
            }
        }

        res.setData(new ArrayList<>(responseMap.values()));

        return res;
    }
}
