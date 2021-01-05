package vn.metech.service;

import vn.metech.dto.tariff.*;
import vn.metech.entity.Tariff;
import vn.metech.exception.tariff.TariffNotFoundException;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface ITariffService extends IService<Tariff> {

	TariffDetailResponse createTariff(TariffCreateRequest tariffRequest, String userId, String remoteAddr);

//	BigDecimal getTariffPrice(AppService appService);

	TariffDetailResponse updateTariff(
					TariffUpdateRequest tariffRequest, String userId, String remoteAddr) throws TariffNotFoundException;

	PagedResult<TariffListResponse> findTariffWithPaging(
					TariffFilterPageRequest tariffRequest, String userId, String remoteAddr);
}
