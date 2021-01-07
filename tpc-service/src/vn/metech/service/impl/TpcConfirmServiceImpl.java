package vn.metech.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.metech.dto.MtTpcConfirmFilter;
import vn.metech.dto.msg.TpcMessage;
import vn.metech.dto.response.MtTpcConfirmListResponse;
import vn.metech.entity.TpcConfirm;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.ITpcConfirmRepository;
import vn.metech.service.TpcConfirmService;
import vn.metech.service.LocalService;
import vn.metech.shared.PagedResult;
import vn.metech.util.DateUtils;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class TpcConfirmServiceImpl extends ServiceImpl<TpcConfirm> implements TpcConfirmService {

	private LocalService localService;
	private ITpcConfirmRepository tpcConfirmRepository;
	private final int duplicateInDays;

	public TpcConfirmServiceImpl(
					LocalService localService,
					ITpcConfirmRepository tpcConfirmRepository,
					@Value("${mbf.request.duplicate-in-days:7}") int duplicateInDays) {
		super(tpcConfirmRepository);
		this.localService = localService;
		this.duplicateInDays = duplicateInDays;
		this.tpcConfirmRepository = tpcConfirmRepository;
	}

	@Override
	public TpcConfirm getConfirmBy(TpcMessage tpcMessage) {
		Date duplicateAfter = DateUtils.addDay(tpcMessage.getCreatedDate(), -duplicateInDays);
		TpcConfirm tpcConfirm = tpcConfirmRepository
						.getConfirmBy(tpcMessage.getPhoneNumber(), tpcMessage.getCreatedDate(), duplicateAfter);
		if (tpcConfirm != null) {
			return tpcConfirm;
		}

		tpcConfirm = new TpcConfirm();
		tpcConfirm.setCheckedDate(tpcMessage.getCreatedDate());
		tpcConfirm.setCheckedDateTime(tpcMessage.getCreatedDate());
		tpcConfirm.setPhoneNumber(tpcMessage.getPhoneNumber());
		tpcConfirm.setIdNumber(tpcMessage.getIdNumber());
		tpcConfirm.setCreatedBy(tpcMessage.getCreatedBy());

		return tpcConfirmRepository.create(tpcConfirm);
	}

	@Override
	public PagedResult<MtTpcConfirmListResponse> getResponsesBy(MtTpcConfirmFilter tpcConfirmFilter, String userId) {
		List<String> userIds = localService.getTpcUserIds();
		if (tpcConfirmFilter == null ||
						tpcConfirmFilter.getPhoneNumber() == null || tpcConfirmFilter.getPhoneNumber().length() < 6) {
			return new PagedResult<>();
		}
		PagedResult<TpcConfirm> pagedRequests = tpcConfirmRepository.getResponsesBy(tpcConfirmFilter, userIds);

		return new PagedResult<>(
						pagedRequests.getTotalRecords(),
						MtTpcConfirmListResponse.fromCollections(pagedRequests.getData())
		);
	}
}
