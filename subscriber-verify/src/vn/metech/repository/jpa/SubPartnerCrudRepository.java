package vn.metech.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.SubPartner;

import java.util.List;

public interface SubPartnerCrudRepository extends CrudRepository<SubPartner, String> {

//    @Query("SELECT sub FROM SubPartner sub WHERE sub.id = ?1 ")
//    SubPartner getSubPartnerById(String subPartnerId);

//    @Query("SELECT sub FROM SubPartner sub WHERE sub.partnerId = ?1")
//    List<SubPartner> getSubPartnersBy(String partnerId);

    List<SubPartner> findAllByPartnerId(String partnerId);
}
