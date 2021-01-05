package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.dto.tariff.*;
import vn.metech.entity.Tariff;
import vn.metech.entity.TariffDetail;
import vn.metech.exception.tariff.TariffNotFoundException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.ITariffDetailRepository;
import vn.metech.repository.ITariffRepository;
import vn.metech.repository.jpa.TariffCrudRepository;
import vn.metech.repository.jpa.TariffDeatilCrudRepository;
import vn.metech.service.ITariffService;
import vn.metech.shared.PagedResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TariffServiceImpl extends ServiceImpl<Tariff> implements ITariffService {

	private ITariffRepository tariffRepository;
	private ITariffDetailRepository tariffDetailRepository;
	private TariffCrudRepository tariffCrudRepository;
	private TariffDeatilCrudRepository tariffDeatilCrudRepository;

	public TariffServiceImpl(
					ITariffRepository tariffRepository,
					ITariffDetailRepository tariffDetailRepository,
					TariffCrudRepository tariffCrudRepository,
					TariffDeatilCrudRepository tariffDeatilCrudRepository) {
		super(tariffRepository);
		this.tariffRepository = tariffRepository;
		this.tariffDetailRepository = tariffDetailRepository;
		this.tariffCrudRepository = tariffCrudRepository;
		this.tariffDeatilCrudRepository = tariffDeatilCrudRepository;
	}

	@Override
	public TariffDetailResponse createTariff(TariffCreateRequest tariffRequest, String userId, String remoteAddr) {
		Tariff tariff = new Tariff();
		tariff.setPrice(tariffRequest.getPrice());
		tariff.setCombo(tariffRequest.getServices().size() > 1);
		tariffRepository.create(tariff);

		List<TariffDetail> tariffDetails = new ArrayList<>();
		for (TariffDetailDto tariffDetailDto : tariffRequest.getServices()) {
			tariffDetails.add(new TariffDetail(tariffDetailDto, tariff.getId()));
		}
		tariffDetailRepository.batchCreate(tariffDetails);

		return TariffDetailResponse.parse(tariff);
	}

	@Override
	public TariffDetailResponse updateTariff(
					TariffUpdateRequest tariffRequest, String userId, String remoteAddr) throws TariffNotFoundException {
		Tariff tariff = tariffRepository.getById(tariffRequest.getTariffId());
		if (tariff == null) {
			throw new TariffNotFoundException(tariffRequest.getTariffId());
		}

		tariff.setPrice(tariffRequest.getPrice());
		tariff.setCombo(tariffRequest.getServices().size() > 1);
		tariffRepository.update(tariff);
		tariffDeatilCrudRepository.deleteTariffDetailsBy(tariff.getId());

		List<TariffDetail> tariffDetails = new ArrayList<>();
		for (TariffDetailDto tariffDetailDto : tariffRequest.getServices()) {
			tariffDetails.add(new TariffDetail(tariffDetailDto, tariff.getId()));
		}
		tariffDetailRepository.batchCreate(tariffDetails);

		return TariffDetailResponse.parse(tariff);
	}

	@Override
	public PagedResult<TariffListResponse> findTariffWithPaging(
					TariffFilterPageRequest tariffRequest, String userId, String remoteAddr) {
		PagedResult<Tariff> tariffPagedResult = tariffRepository.findTariffWithPaging(tariffRequest);
		return new PagedResult<>(
						tariffPagedResult.getTotalRecords(),
						TariffListResponse.fromCollections(tariffPagedResult.getData())
		);
	}
}
