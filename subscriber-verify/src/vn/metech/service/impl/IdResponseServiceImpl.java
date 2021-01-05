package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.dto.MtIdFilter;
import vn.metech.dto.response.MtIdResponse;
import vn.metech.entity.IdRequest;
import vn.metech.entity.IdResponse;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.IIdRequestRepository;
import vn.metech.repository.IIdResponseRepository;
import vn.metech.service.IIdResponseService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IdResponseServiceImpl extends ServiceImpl<IdResponse> implements IIdResponseService {

    private LocalService localService;
    private IIdRequestRepository idRequestRepository;
    private IIdResponseRepository idResponseRepository;

    public IdResponseServiceImpl(
            LocalService localService,
            IIdRequestRepository idRequestRepository,
            IIdResponseRepository idResponseRepository) {
        super(idResponseRepository);
        this.localService = localService;
        this.idRequestRepository = idRequestRepository;
        this.idResponseRepository = idResponseRepository;
    }

    @Override
    public PagedResult<MtIdResponse> getPastIdResponsesFrom(MtIdFilter idFilter, String userId) {
        List<String> userIds = localService.getRecordsUserIds(userId);
        if (userIds == null || userIds.isEmpty()) {
            return new PagedResult<>();
        }
        PagedResult<IdRequest> pagedRequests = idRequestRepository.getRequestsIncludeResponseBy(idFilter, userIds);
        List<MtIdResponse> mtIdResponses = new ArrayList<>();
        for (IdRequest idRequest : pagedRequests.getData()) {
            IdResponse idResponse = idRequest.getIdResponse();
            if (idResponse == null) continue;

            mtIdResponses.add(new MtIdResponse(idResponse));
        }

        return new PagedResult<>(pagedRequests.getTotalRecords(), mtIdResponses);
    }

//    @Override
//    public void updateMbfStatus(String responseId, MbfStatus mbfStatus) {
//        idResponseRepository.updateMbfStatus(responseId, mbfStatus);
//    }
//
//    @Override
//    public void updateSubscriberStatus(String responseId, SubscriberStatus subscriberStatus) {
//        idResponseRepository.updateSubscriberStatus(responseId, subscriberStatus);
//    }
}
