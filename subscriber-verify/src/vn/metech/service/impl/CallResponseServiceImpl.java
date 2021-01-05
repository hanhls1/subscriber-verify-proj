package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.MbfStatus;
import vn.metech.constant.RequestType;
import vn.metech.dto.MbfCallReferenceResult;
import vn.metech.dto.MtCallFilter;
import vn.metech.dto.request.MtCallFilterRequest;
import vn.metech.dto.response.MtCallReferenceListResponse;
import vn.metech.dto.response.MtCallResponse;
import vn.metech.dto.response.MtCallVerifyResponse;
import vn.metech.entity.CallRequest;
import vn.metech.entity.CallResponse;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.ICallRequestRepository;
import vn.metech.repository.ICallResponseRepository;
import vn.metech.service.ICallResponseService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;
import vn.metech.util.JsonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CallResponseServiceImpl
				extends ServiceImpl<CallResponse> implements ICallResponseService {

	private LocalService localService;
	private ICallRequestRepository callRequestRepository;
	private ICallResponseRepository callResponseRepository;


	public CallResponseServiceImpl(
					LocalService localService,
					ICallRequestRepository callRequestRepository,
					ICallResponseRepository callResponseRepository) {
		super(callResponseRepository);
		this.localService = localService;
		this.callRequestRepository = callRequestRepository;
		this.callResponseRepository = callResponseRepository;

	}

	@Override
	public CallResponse update(CallResponse callResponse) {
		callResponse.setUpdatedDate(new Date());

		return super.update(callResponse);
	}

	@Override
	public PagedResult<MtCallResponse> getMtCallsVerifyResponseFrom(
					MtCallFilterRequest mtCallRequest, String userId, String remoteAddress) {
		List<String> userIds = localService.getRecordsUserIds(userId);
		if (userIds == null || userIds.isEmpty()) {
			return new PagedResult<>();
		}
		PagedResult<CallRequest> pagedResult = callRequestRepository
						.getRequestsIncludeResponseBy(mtCallRequest.toFilter(), RequestType.VERIFY, userIds);

		List<MtCallResponse> mtCallResponses = new ArrayList<>();
		for (CallRequest callRequest : pagedResult.getData()) {
			CallResponse callResponse = callRequest.getCallResponse();
			if (callResponse == null) continue;

			MtCallResponse mtCallResponse = new MtCallResponse(
							callRequest.getId(),
							callResponse.getId(),
							callRequest.getPhoneNumber()
			);
			mtCallResponses.add(mtCallResponse);
			if (callResponse.getMbfStatus() != MbfStatus.SUCCESS) continue;

			mtCallResponse.applyMbfStatus(callResponse.getMbfStatus());
			MbfCallReferenceResult callResult = JsonUtils
							.toObject(callResponse.getResponseData(), MbfCallReferenceResult.class);
			if (callResult == null ) continue;


			MtCallVerifyResponse mtCallVerifyResponse = new MtCallVerifyResponse(callResult.getCallFrequency());
			mtCallVerifyResponse.setStatus1(callResult.getCallStatus().getStatus1());
			mtCallVerifyResponse.setStatus2(callResult.getCallStatus().getStatus2());

			mtCallResponse.setData(mtCallVerifyResponse);
		}

		return new PagedResult<>(pagedResult.getTotalRecords(), mtCallResponses);
	}

	@Override
	public PagedResult<MtCallReferenceListResponse> getCallsBasicResponse(MtCallFilter toFilter, String userId, String remoteAddr) {
		List<String> userIds = localService.getRecordsUserIds(userId);
		if (userIds == null || userIds.isEmpty()) {
			return new PagedResult<>();
		}

		PagedResult<CallResponse> callResponses =
						callResponseRepository.getResponsesBy(toFilter, RequestType.BASIC, userIds);

		return new PagedResult<>(
						callResponses.getTotalRecords(),
						MtCallReferenceListResponse.fromCollection(callResponses.getData())
		);
	}

}
