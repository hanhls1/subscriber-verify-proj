package vn.metech.repository.impl;

import org.springframework.stereotype.Repository;
import vn.metech.entity.BalanceSummaryDetail;
import vn.metech.jpa.repository.RepositoryImpl;
import vn.metech.repository.IBalanceSummaryDetailRepository;

import javax.persistence.EntityManager;

@Repository
public class BalanceSummaryDetailRepositoryImpl
				extends RepositoryImpl<BalanceSummaryDetail> implements IBalanceSummaryDetailRepository {

	public BalanceSummaryDetailRepositoryImpl(EntityManager entityManager) {
		super(entityManager, BalanceSummaryDetail.class);
	}

}
