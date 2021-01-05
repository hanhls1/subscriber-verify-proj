package vn.metech.repository;

import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.dto.request.SubPartnerFilterRequest;
import vn.metech.entity.SubPartner;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;
import vn.metech.dto.response.SubPartnerResponse;

import java.util.List;

public interface ISubPartnerRepository extends IRepository<SubPartner> {

	List<SubPartner> getSubPartnersBy(SubPartnerFilterRequest subPartnerFilterRequest);

	PagedResult<SubPartner> getSubPartnersWithPaging(PartnerPageRequest partnerPageRequest);

	List<SubPartnerResponse> getConfirmInfoSubPartnerIdBy(String partnerId);
}
