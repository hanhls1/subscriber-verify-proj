package vn.metech.repository;

import vn.metech.dto.tariff.TariffFilterPageRequest;
import vn.metech.entity.Tariff;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

public interface ITariffRepository extends IRepository<Tariff> {

	PagedResult<Tariff> findTariffWithPaging(TariffFilterPageRequest tariffRequest);

}
