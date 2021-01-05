package vn.metech.service;

import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.SubPartnerCreateRequest;
import vn.metech.dto.request.SubPartnerFilterRequest;
import vn.metech.dto.request.SubPartnerUpdateRequest;
import vn.metech.dto.response.SubPartnerListResponse;
import vn.metech.entity.SubPartner;
import vn.metech.exception.PartnerNotFoundException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface ISubPartnerService extends IService<SubPartner> {

	List<SubPartnerListResponse> fillSubPartnersBy(
					SubPartnerFilterRequest subPartnerFilterRequest, String userId) throws PartnerNotFoundException;

	List<String> getCustomerCodesBy(String partnerId);

	SubPartnerListResponse createSubPartner(
					SubPartnerCreateRequest subPartnerCreateRequest, String userId) throws PartnerNotFoundException;

	SubPartnerListResponse updateSubPartner(
					SubPartnerUpdateRequest subPartnerUpdateRequest, String userId) throws PartnerNotFoundException;

	PagedResult<SubPartnerListResponse> getPartnersWithPaging(PartnerPageRequest partnerPageRequest, String userId);
}
