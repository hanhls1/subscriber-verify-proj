package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.SubPartnerCreateRequest;
import vn.metech.dto.request.SubPartnerFilterRequest;
import vn.metech.dto.request.SubPartnerUpdateRequest;
import vn.metech.dto.response.SubPartnerListResponse;
import vn.metech.entity.Partner;
import vn.metech.entity.SubPartner;
import vn.metech.exception.PartnerNotFoundException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.IPartnerRepository;
import vn.metech.repository.ISubPartnerRepository;
import vn.metech.repository.jpa.PartnerCrudRepository;
import vn.metech.repository.jpa.SubPartnerCrudRepository;
import vn.metech.service.ISubPartnerService;
import vn.metech.shared.PagedResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SubPartnerServiceImpl extends ServiceImpl<SubPartner> implements ISubPartnerService {

	private IPartnerRepository partnerRepository;
	private ISubPartnerRepository subPartnerRepository;
	private PartnerCrudRepository partnerCrudRepository;
	private SubPartnerCrudRepository subPartnerCrudRepository;

	public SubPartnerServiceImpl(
					IPartnerRepository partnerRepository,
					ISubPartnerRepository subPartnerRepository,
					PartnerCrudRepository partnerCrudRepository,
					SubPartnerCrudRepository subPartnerCrudRepository) {
		super(subPartnerRepository);
		this.partnerRepository = partnerRepository;
		this.subPartnerRepository = subPartnerRepository;
		this.partnerCrudRepository = partnerCrudRepository;
		this.subPartnerCrudRepository = subPartnerCrudRepository;
	}

	@Override
	public List<SubPartnerListResponse> fillSubPartnersBy(
					SubPartnerFilterRequest subPartnerFilterRequest, String userId) throws PartnerNotFoundException {
		Partner partner = partnerRepository.getById(subPartnerFilterRequest.getPartnerId());
		if (partner == null || partner.isDeleted()) {
			throw new PartnerNotFoundException();
		}

		return SubPartnerListResponse.fromCollection(
						subPartnerRepository.getSubPartnersBy(subPartnerFilterRequest)
		);
	}

	@Override
	public List<String> getCustomerCodesBy(String partnerId) {
//		List<SubPartner> subPartners = subPartnerRepository.getSubPartnersBy(partnerId);
		List<SubPartner> subPartners = subPartnerCrudRepository.findAllByPartnerId(partnerId);
		List<String> customerCodes = new ArrayList<>();

		for (SubPartner subPartner : subPartners) {
			customerCodes.add(subPartner.getCustomerCode());
		}

		return customerCodes;
	}

	@Override
	public SubPartnerListResponse createSubPartner(
					SubPartnerCreateRequest subPartnerCreateRequest, String userId) throws PartnerNotFoundException {
		Partner partner = partnerCrudRepository.findById(subPartnerCreateRequest.getPartnerId()).get();
//		Partner partner = partnerCrudRepository.getPartnerById(subPartnerCreateRequest.getPartnerId());
		if (partner == null) {
			throw new PartnerNotFoundException(subPartnerCreateRequest.getPartnerId());
		}
		SubPartner subPartner = subPartnerCreateRequest.toSubPartner();
		subPartner.setCreatedBy(userId);
		subPartner.setPartnerId(partner.getId());
		subPartner.setPartnerCode(partner.getPartnerCode());
		subPartnerRepository.create(subPartner);

		return SubPartnerListResponse.from(subPartner);
	}

	@Override
	public SubPartnerListResponse updateSubPartner(
					SubPartnerUpdateRequest subPartnerUpdateRequest, String userId) throws PartnerNotFoundException {
		SubPartner subPartner = subPartnerCrudRepository.findById(subPartnerUpdateRequest.getSubPartnerId()).get();
//		SubPartner subPartner = subPartnerCrudRepository.getSubPartnerById(subPartnerUpdateRequest.getSubPartnerId());

		if (subPartner == null) {
			throw new PartnerNotFoundException(subPartnerUpdateRequest.getSubPartnerId());
		}
		if (!subPartnerUpdateRequest.getPartnerId().equalsIgnoreCase(subPartner.getPartnerId())) {
			Partner partner = partnerCrudRepository.findById(subPartnerUpdateRequest.getPartnerId()).get();
//			Partner partner = partnerCrudRepository.getPartnerById(subPartnerUpdateRequest.getPartnerId());
			if (partner == null) {
				throw new PartnerNotFoundException(subPartnerUpdateRequest.getPartnerId());
			}
			subPartner.setPartnerId(partner.getId());
			subPartner.setPartnerCode(partner.getPartnerCode());
		}
		subPartner = subPartnerUpdateRequest.toSubPartner(subPartner);
		subPartnerRepository.update(subPartner);

		return SubPartnerListResponse.from(subPartner);
	}

	@Override
	public PagedResult<SubPartnerListResponse> getPartnersWithPaging(
					PartnerPageRequest partnerPageRequest, String userId) {
		PagedResult<SubPartner> subPartners = subPartnerRepository.getSubPartnersWithPaging(partnerPageRequest);
		return new PagedResult<>(
						subPartners.getTotalRecords(),
						SubPartnerListResponse.fromCollection(subPartners.getData())
		);
	}
}
