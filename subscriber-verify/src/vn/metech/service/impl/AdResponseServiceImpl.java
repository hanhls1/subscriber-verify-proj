package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.dto.MtAdFilter;
import vn.metech.dto.response.MtAdResponse;
import vn.metech.entity.AdRequest;
import vn.metech.entity.AdResponse;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.IAdRequestRepository;
import vn.metech.repository.IAdResponseRepository;
import vn.metech.service.IAdResponseService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdResponseServiceImpl extends ServiceImpl<AdResponse> implements IAdResponseService {

	private LocalService localService;
	private IAdRequestRepository adRequestRepository;
	private IAdResponseRepository adResponseRepository;

	public AdResponseServiceImpl(
					LocalService localService,
					IAdRequestRepository adRequestRepository,
					IAdResponseRepository adResponseRepository) {
		super(adResponseRepository);
		this.localService = localService;
		this.adRequestRepository = adRequestRepository;
		this.adResponseRepository = adResponseRepository;
	}


	@Override
	public PagedResult<MtAdResponse> getImeiResponsesFrom(MtAdFilter adFilter, String userId) {
		List<String> userIds = localService.getRecordsUserIds(userId);
		if (userIds == null || userIds.isEmpty()) {
			return new PagedResult<>();
		}

		PagedResult<AdRequest> pagedRequests = adRequestRepository.getRequestsIncludeResponseBy(adFilter, userIds);
		List<MtAdResponse> mtAdResponses = new ArrayList<>();
		for (AdRequest adRequest : pagedRequests.getData()) {
			AdResponse adResponse = adRequest.getAdResponse();
			if (adResponse == null) continue;

			mtAdResponses.add(new MtAdResponse(adResponse));
		}

		return new PagedResult<>(pagedRequests.getTotalRecords(), mtAdResponses);
	}

//	@Override
//	public void updateMbfStatus(String id, MbfStatus mbfStatus) {
//		adResponseRepository.updateMbfStatus(id, mbfStatus);
//	}
//
//	@Override
//	public void updateSubscriberStatus(String id, SubscriberStatus subscriberStatus) {
//		adResponseRepository.updateSubscriberStatus(id, subscriberStatus);
//	}
//
//	@Override
//	public void updateResponseData(String id, String jsonData, ResponseStatus responseStatus) {
//		adResponseRepository.updateResponseData(id, jsonData, responseStatus);
//	}

	//@Override
	//public FC02Response getResponse(String customerRequestId, String userId) throws RequestNotFoundException {
	//	AdRequest adRequest = adRequestRepository.findRequestIncludeResponseBy(customerRequestId, userId);
	//	if (adRequest == null || adRequest.getAdResponse() == null) {
	//		throw new RequestNotFoundException(customerRequestId);
	//	}
//
//		return new FC02Response(adRequest);
//	}

}
