package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.Partner;
import vn.metech.dto.response.PartnerResponse;

import java.util.List;

public interface PartnerCrudRepository extends CrudRepository<Partner, String> {


    Partner findByPartnerCode(String partnerCode);

    long countByPartnerCode(String partnerCode);

    @Query("select new vn.metech.dto.response.PartnerResponse( pa.id, pa.name)" +
            " from Partner pa where pa.id is not null group by pa.id, pa.name ORDER BY pa.name")
    List<PartnerResponse> getListPartner();


}
