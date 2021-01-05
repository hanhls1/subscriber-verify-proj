package vn.metech.service;

import org.springframework.stereotype.Service;
import vn.metech.dto.ConfirmInfoFilterIdResponse;
import vn.metech.dto.request.aio.ConfirmInfoListRequest;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.aio.ConfirmInfoRequestResponse;
import vn.metech.entity.ConfirmInfo;
import vn.metech.repository.ConfirmInfoRepository;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;

import java.util.Collections;

@Service
public class ConfirmInfoSearchService {

    private final LocalService localService;
    private final ConfirmInfoRepository confirmInfoRepository;
    private final ConfirmInfoCrudRepository confirmInfoCrudRepository;

    public ConfirmInfoSearchService(
            LocalService localService,
            ConfirmInfoRepository confirmInfoRepository,
            ConfirmInfoCrudRepository confirmInfoCrudRepository) {
        this.localService = localService;
        this.confirmInfoRepository = confirmInfoRepository;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
    }

    public PageResponse<ConfirmInfoRequestResponse> findBy(ConfirmInfoListRequest confirmInfoListRequest, String userId) {
        ConfirmInfoFilterIdResponse ids = confirmInfoRepository.fillConfirmInfoIds(
                confirmInfoListRequest.getPhoneNumber(),
                confirmInfoListRequest.getServiceType(),
                confirmInfoListRequest.getFromDate(),
                confirmInfoListRequest.getToDate(),
                Collections.singletonList(userId),
//                localService.getRecordsUserIds(userId),
                confirmInfoListRequest
        );
        Iterable<ConfirmInfo> confirmInfoLst = confirmInfoCrudRepository.findIncludeParamsByIdIn(ids.getConfirmInfoIds());

        return new PageResponse<>(ids.getTotal(), ConfirmInfoRequestResponse.of(confirmInfoLst), confirmInfoListRequest);
    }

}
