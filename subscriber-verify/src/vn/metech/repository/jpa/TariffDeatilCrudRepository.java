package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.TariffDetail;

public interface TariffDeatilCrudRepository extends CrudRepository<TariffDetail, String> {

    @Query("DELETE FROM TariffDetail td WHERE td.tariffId = ?1")
    void deleteTariffDetailsBy(String tariffId);
}
