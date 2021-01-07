package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.RequestType;
import vn.metech.constant.ResponseStatus;
import vn.metech.dto.MtInfoDiscoveryFilter;
import vn.metech.dto.request.MtResponseRequest;
import vn.metech.dto.response.MtInfoDiscoveryResponse;
import vn.metech.dto.response.MtKyc02Response;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.exception.NoHaveResultException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.IInfoDiscoveryRequestRepository;
import vn.metech.repository.IInfoDiscoveryResponseRepository;
import vn.metech.service.IInfoDiscoveryResponseService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InfoDiscoveryResponseServiceImpl
				extends ServiceImpl<InfoDiscoveryResponse> implements IInfoDiscoveryResponseService {

	private LocalService localService;
	private IInfoDiscoveryRequestRepository idRequestRepository;
	private IInfoDiscoveryResponseRepository idResponseRepository;

	public InfoDiscoveryResponseServiceImpl(
					LocalService localService,
					IInfoDiscoveryRequestRepository idRequestRepository,
					IInfoDiscoveryResponseRepository idResponseRepository) {
		super(idResponseRepository);
		this.localService = localService;
		this.idRequestRepository = idRequestRepository;
		this.idResponseRepository = idResponseRepository;
	}

	@Override
	public InfoDiscoveryResponse getQuestionSentResponseIncludeRequestsBy(String responseId) {
		return idResponseRepository
						.getResponseIncludeRequestsBy(responseId, ResponseStatus.QUESTION_SENT);
	}

	@Override
	public PagedResult<MtInfoDiscoveryResponse> getResponsesBy(
					MtInfoDiscoveryFilter infoDiscoveryFilter, RequestType requestType, String userId) {
		List<String> userIds = localService.getRefUserIds(userId);
		PagedResult<InfoDiscoveryResponse> pagedRequests =
						idResponseRepository.getResponsesBy(infoDiscoveryFilter, requestType, userIds);
		List<MtInfoDiscoveryResponse> infoDiscoveryResponses = new ArrayList<>();
		for (InfoDiscoveryResponse infoDiscoveryResponse : pagedRequests.getData()) {
			MtInfoDiscoveryResponse mtInfoDiscoveryResponse =
							new MtInfoDiscoveryResponse(infoDiscoveryResponse);
			if (mtInfoDiscoveryResponse == null) {
				continue;
			}
			infoDiscoveryResponses.add(mtInfoDiscoveryResponse);
		}

		return new PagedResult<>(pagedRequests.getTotalRecords(), infoDiscoveryResponses);
	}

	@Override
	public MtKyc02Response findResponseBy(MtResponseRequest mtResponseRequest, String userId)
					throws NoHaveResultException {
		List<String> userIds = localService.getRefUserIds(userId);
		InfoDiscoveryRequest existRequest = idRequestRepository.getCustomerRequestIncludeResponseBy(
						mtResponseRequest.getQuestionRequestId(), userIds, RequestStatus.ANSWER_RECEIVED, RequestStatus.TIMEOUT);
		if (existRequest == null || existRequest.getInfoDiscoveryResponse() == null) {
			throw new NoHaveResultException(mtResponseRequest.getQuestionRequestId());
		}
		return new MtKyc02Response(existRequest.getCustomerRequestId(), existRequest.getInfoDiscoveryResponse());
	}
}
