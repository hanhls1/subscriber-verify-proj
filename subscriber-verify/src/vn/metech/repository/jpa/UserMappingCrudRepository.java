package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.UserMapping;

import java.util.List;

public interface UserMappingCrudRepository extends CrudRepository<UserMapping, String> {

    List<UserMapping> findAllByUserId(String userId);

    long countIdByUserIdAndRefUserId(String userId, String refUserId);

    @Query("select um from UserMapping um where (um.userId = :keyword or um.email like %:keyword%)")
    List<UserMapping> findUserWithMappingsBy(String keyword);

}
