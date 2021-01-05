package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.RequestType;
import vn.metech.dto.request.MtRegularlyLocationRequest;
import vn.metech.dto.response.MtAdvanceLocationResponse;
import vn.metech.entity.LocationResponse;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.ILocationRequestRepository;
import vn.metech.repository.ILocationResponseRepository;
import vn.metech.service.ILocationResponseService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LocationResponseServiceImpl
        extends ServiceImpl<LocationResponse> implements ILocationResponseService {

    private LocalService localService;
    private ILocationRequestRepository locationRequestRepository;
    private ILocationResponseRepository locationResponseRepository;

    public LocationResponseServiceImpl(
            LocalService localService,
            ILocationRequestRepository locationRequestRepository,
            ILocationResponseRepository locationResponseRepository) {
        super(locationResponseRepository);
        this.localService = localService;
        this.locationRequestRepository = locationRequestRepository;
        this.locationResponseRepository = locationResponseRepository;
    }

    @Override
    public LocationResponse update(LocationResponse locationResponse) {
        locationResponse.setUpdatedDate(new Date());

        return super.update(locationResponse);
    }


//	@Override
//	public PagedResult<MtRegularlyLocationResponse> getRegularlyLocationResponsesFrom(
//					MtRegularlyLocationRequest mtLocationRequest, String userId, String remoteAddress) {
//		List<String>
//		PagedResult<LocationRequest> pagedRequests = locationRequestRepository
//						.getRequestsIncludeResponseBy(mtLocationRequest.toFilter(), RequestType.REGULARLY, userId);
//		List<MtRegularlyLocationResponse> regularlyLocationResponses = new ArrayList<>();
//		for (LocationRequest locationRequest : pagedRequests.getData()) {
//			LocationResponse locationResponse = locationRequest.getLocationResponse();
//			if (locationRequest == null) {
//				continue;
//			}
//
//			MtRegularlyLocationResponse regularlyLocationResponse =
//							new MtRegularlyLocationResponse(
//											locationRequest.getCustomerRequestId(),
//											locationResponse.getId(),
//											locationRequest.getPhoneNumber());
//			regularlyLocationResponses.add(regularlyLocationResponse);
//			if (locationResponse.getMbfStatus() == null) {
//				continue;
//			}
//
//			regularlyLocationResponse.applyMbfStatus(locationResponse.getMbfStatus());
//			if (locationResponse.getMbfStatus() != MbfStatus.SUCCESS) {
//				continue;
//			}
//
//			MbfLocationResult locationResult = JsonUtils
//							.toObject(locationResponse.getResponseData(), MbfLocationResult.class);
//			regularlyLocationResponse.addRegularlyLocationResult(locationResult.getRegularlyLocations());
//		}
//
//		return new PagedResult<>(pagedRequests.getTotalRecords(), regularlyLocationResponses);
//	}

    @Override
    public PagedResult<MtAdvanceLocationResponse> getAdvanceLocationResponses(
            MtRegularlyLocationRequest mtLocationRequest, String userId, String remoteAddress) {
        List<String> userIds = localService.getRecordsUserIds(userId);
        if (userIds == null || userIds.isEmpty()) {
            return new PagedResult<>();
        }

        PagedResult<LocationResponse> pagedResult = locationResponseRepository
                .getResponsesBy(mtLocationRequest.toFilter(), RequestType.TPC_LOCATION_ADVANCE_VERIFY, userIds);

        return new PagedResult<>(
                pagedResult.getTotalRecords(),
                MtAdvanceLocationResponse.fromCollection(pagedResult.getData())
        );
    }

//    @Override
//    public void updateMbfStatus(String responseId, MbfStatus mbfStatus) {
//        locationResponseRepository.updateMbfStatus(responseId, mbfStatus);
//    }
//
//    @Override
//    public void updateSubscriberStatus(String responseId, SubscriberStatus subscriberStatus) {
//        locationResponseRepository.updateSubscriberStatus(responseId, subscriberStatus);
//    }
}
