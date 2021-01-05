package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.dto.request.PartnerCreateRequest;
import vn.metech.dto.request.PartnerFilterRequest;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.PartnerUpdateRequest;
import vn.metech.dto.response.PartnerListResponse;
import vn.metech.entity.Partner;
import vn.metech.exception.PartnerDuplicateException;
import vn.metech.exception.PartnerNotFoundException;
import vn.metech.repository.IPartnerRepository;
import vn.metech.repository.jpa.PartnerCrudRepository;
import vn.metech.service.IPartnerService;
import vn.metech.shared.PagedResult;

import java.util.List;

@Service
@Transactional
public class PartnerServiceImpl implements IPartnerService {

	private IPartnerRepository partnerRepository;
	private PartnerCrudRepository partnerCrudRepository;

	public PartnerServiceImpl(
					IPartnerRepository partnerRepository,
					PartnerCrudRepository partnerCrudRepository) {
		this.partnerRepository = partnerRepository;
		this.partnerCrudRepository = partnerCrudRepository;
	}

	@Override
	public List<PartnerListResponse> fillPartnersBy(
					PartnerFilterRequest partnerFilterRequest, String userId) {

		return PartnerListResponse.fromCollection(partnerRepository.fillPartnersBy(partnerFilterRequest));
	}

	@Override
	public PartnerListResponse createPartner(
					PartnerCreateRequest partnerCreateRequest, String userId) throws PartnerDuplicateException {
		long partnerCodeCounter = partnerCrudRepository.countByPartnerCode(partnerCreateRequest.getPartnerCode());
		if (partnerCodeCounter > 0) {
			throw new PartnerDuplicateException(partnerCreateRequest.getPartnerCode());
		}
		Partner partner = partnerCreateRequest.toPartner();
		partner.setPartnerCode(partnerCreateRequest.getPartnerCode());
		partner.setCreatedBy(userId);
		partnerRepository.create(partner);

		return PartnerListResponse.from(partner);
	}

	@Override
	public PartnerListResponse updatePartner(
					PartnerUpdateRequest partnerUpdateRequest, String userId) throws PartnerNotFoundException {
		Partner partner = partnerCrudRepository.findById(partnerUpdateRequest.getPartnerId()).get();
//        Partner partner = partnerCrudRepository.getPartnerById(partnerUpdateRequest.getPartnerId());
		if (partner == null) {
			throw new PartnerNotFoundException(partnerUpdateRequest.getPartnerId());
		}
		partner = partnerUpdateRequest.toPartner(partner);
		partner.setUpdatedBy(userId);
		partnerRepository.update(partner);

		return PartnerListResponse.from(partner);
	}

	@Override
	public PagedResult<PartnerListResponse> getPartnersWithPaging(PartnerPageRequest partnerPageRequest, String userId) {
		PagedResult<Partner> partners = partnerRepository.getPartnersWithPaging(partnerPageRequest);

		return new PagedResult<>(partners.getTotalRecords(), PartnerListResponse.fromCollection(partners.getData()));
	}
}
