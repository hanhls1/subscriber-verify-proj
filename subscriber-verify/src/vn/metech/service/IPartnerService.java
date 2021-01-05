package vn.metech.service;

import vn.metech.dto.request.PartnerCreateRequest;
import vn.metech.dto.request.PartnerFilterRequest;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.PartnerUpdateRequest;
import vn.metech.dto.response.PartnerListResponse;
import vn.metech.exception.PartnerDuplicateException;
import vn.metech.exception.PartnerNotFoundException;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface IPartnerService {

	List<PartnerListResponse> fillPartnersBy(PartnerFilterRequest partnerFilterRequest, String userId);

	PartnerListResponse createPartner(
					PartnerCreateRequest partnerCreateRequest, String userId) throws PartnerDuplicateException;

	PartnerListResponse updatePartner(
					PartnerUpdateRequest partnerUpdateRequest, String userId) throws PartnerNotFoundException;

	PagedResult<PartnerListResponse> getPartnersWithPaging(PartnerPageRequest partnerPageRequest, String userId);
}
