package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.repository.IBalanceSummaryRepository;
import vn.metech.service.IBalanceSummaryService;

@Service
@Transactional
public class BalanceSummaryServiceImpl implements IBalanceSummaryService {

	private IBalanceSummaryRepository balanceSummaryRepository;

	public BalanceSummaryServiceImpl(IBalanceSummaryRepository balanceSummaryRepository) {
		this.balanceSummaryRepository = balanceSummaryRepository;
	}
}
