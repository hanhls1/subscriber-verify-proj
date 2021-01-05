package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.metech.entity.BalanceTransaction;

public interface BalanceTransactionCrudRepository extends JpaRepository<BalanceTransaction, String> {

//    @Query("SELECT btr FROM BalanceTransaction btr WHERE btr.userId = ?1 AND btr.transaction = ?2")
//    BalanceTransaction getBalanceTransactionBy(String requestId, Transaction transaction);

}
