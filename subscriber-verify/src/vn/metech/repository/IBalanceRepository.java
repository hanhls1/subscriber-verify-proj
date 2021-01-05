package vn.metech.repository;

import vn.metech.entity.Balance;
import vn.metech.jpa.repository.IRepository;

import java.util.List;

public interface IBalanceRepository extends IRepository<Balance> {

	List<Balance> getActiveBalancesBy(int limit);

}
