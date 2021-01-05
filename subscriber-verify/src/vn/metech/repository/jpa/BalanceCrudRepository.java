package vn.metech.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.Balance;

public interface BalanceCrudRepository extends CrudRepository<Balance, String> {

//    @Query("SELECT b FROM Balance b")
//    List<Balance> getActiveBalancesBy(int limit);
//
//    @Query("SELECT b FROM Balance b WHERE b.userId = ?1")
//    Balance getBalanceBy(String userId);

    Balance findByUserId(String userId);
}
