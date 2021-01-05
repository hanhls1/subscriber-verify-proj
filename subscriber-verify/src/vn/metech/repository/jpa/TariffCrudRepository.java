package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.constant.VerifyService;
import vn.metech.entity.Tariff;

public interface TariffCrudRepository extends CrudRepository<Tariff, String> {

    @Query("SELECT tr FROM Tariff tr WHERE tr.verifyService IN (?1) ORDER BY tr.createdDate")
    Tariff getTariffBy(VerifyService... verifyServices);

}
