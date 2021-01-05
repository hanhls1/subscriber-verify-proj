package vn.metech.repository;

import vn.metech.dto.request.PartnerFilterRequest;
import vn.metech.dto.request.PartnerPageRequest;
import vn.metech.entity.Partner;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface IPartnerRepository extends IRepository<Partner> {

	List<Partner> fillPartnersBy(PartnerFilterRequest partnerRequest);

	PagedResult<Partner> getPartnersWithPaging(PartnerPageRequest partnerPageRequest);
}
