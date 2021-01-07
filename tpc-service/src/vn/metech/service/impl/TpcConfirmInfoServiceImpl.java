package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.metech.constant.AppService;
import vn.metech.dto.msg.TpcMessage;
import vn.metech.entity.TpcConfirm;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.ITpcConfirmInfoRepository;
import vn.metech.service.TpcConfirmInfoService;

@Service
@Transactional
public class TpcConfirmInfoServiceImpl extends ServiceImpl<TpcConfirmInfo> implements TpcConfirmInfoService {

	private ITpcConfirmInfoRepository tpcConfirmInfoRepository;

	public TpcConfirmInfoServiceImpl(ITpcConfirmInfoRepository tpcConfirmInfoRepository) {
		super(tpcConfirmInfoRepository);
		this.tpcConfirmInfoRepository = tpcConfirmInfoRepository;
	}

	@Override
	public TpcConfirmInfo getConfirmInfoBy(TpcMessage tpcMessage, TpcConfirm tpcConfirm) {
		TpcConfirmInfo tpcConfirmInfo = tpcConfirmInfoRepository
						.getConfirmInfoBy(tpcMessage.getRequestId(), tpcConfirm.getId());
		if (tpcConfirmInfo != null) {
			applyResult(tpcConfirmInfo, tpcMessage);

			return tpcConfirmInfoRepository.update(tpcConfirmInfo);
		}

		tpcConfirmInfo = new TpcConfirmInfo();
		tpcConfirmInfo.setTimeout(tpcMessage.getTimeout());
		tpcConfirmInfo.setAppService(tpcMessage.getAppService());
		tpcConfirmInfo.setRequestId(tpcMessage.getRequestId());
		tpcConfirmInfo.setPhoneNumber(tpcMessage.getPhoneNumber());
		tpcConfirmInfo.setConfirmId(tpcConfirm.getId());
		tpcConfirmInfo.setCreatedBy(tpcMessage.getCreatedBy());
		applyResult(tpcConfirmInfo, tpcMessage);

		return tpcConfirmInfoRepository.create(tpcConfirmInfo);
	}

	private void applyResult(TpcConfirmInfo tpcConfirmInfo, TpcMessage tpcMessage) {
		if (tpcMessage.getAppService() == AppService.AD_REFERENCE) {
			if (tpcMessage.getImeiResult() != null) {
				tpcConfirmInfo.setResult1(tpcMessage.getImeiResult().name());
			}
		} else if (tpcMessage.getAppService() == AppService.CALL_REFERENCE) {
			if (tpcMessage.getRefPhone1Result() != null) {
				tpcConfirmInfo.setResult1(tpcMessage.getRefPhone1Result().name());
			}
			if (tpcMessage.getRefPhone2Result() != null) {
				tpcConfirmInfo.setResult2(tpcMessage.getRefPhone2Result().name());
			}
		} else if (tpcMessage.getAppService() == AppService.TPC) {
			tpcConfirmInfo.setResult1(tpcMessage.getScoreResult());
		}
	}
}
