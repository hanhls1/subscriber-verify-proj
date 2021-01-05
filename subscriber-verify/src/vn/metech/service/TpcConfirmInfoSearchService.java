package vn.metech.service;

import org.springframework.stereotype.Service;
import vn.metech.dto.request.TpcConfirmInfoListRequest;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.TpcConfirmInfoListResponse;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.repository.TpcConfirmInfoRepository;

import java.util.List;

@Service
public class TpcConfirmInfoSearchService {

    private LocalService localService;
    private TpcConfirmInfoRepository tpcConfirmInfoRepository;

    public TpcConfirmInfoSearchService(LocalService localService, TpcConfirmInfoRepository tpcConfirmInfoRepository) {
        this.localService = localService;
        this.tpcConfirmInfoRepository = tpcConfirmInfoRepository;
    }

    public PageResponse<TpcConfirmInfoListResponse> getResponsesBy(TpcConfirmInfoListRequest req, String userId) {
        List<String> userIds = localService.getTpcUserIds();
        if (userIds == null || userIds.isEmpty()) {
            return new PageResponse<>();
        }

        PageResponse<TpcConfirmInfo> confirmInfoPageResponse = tpcConfirmInfoRepository.getTpcConfirmInfoBy(
                req.getPhoneNumber(),
                req.getFromDate(),
                req.getToDate(),
                userIds,
                req
        );


        PageResponse<TpcConfirmInfoListResponse> res = new PageResponse<>();
        res.setTotal(confirmInfoPageResponse.getTotal());
        res.setCurrentPage(req.getCurrentPage());
        res.setPageSize(req.getPageSize());
        res.setData(TpcConfirmInfoListResponse.fromCollections(confirmInfoPageResponse.getData()));

        return res;
    }
}
