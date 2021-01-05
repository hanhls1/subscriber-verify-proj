package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.UserRole;

import java.util.List;

public interface UserRoleCrudRepository extends CrudRepository<UserRole, String> {

    @Query("SELECT ur.uri FROM UserRole ur WHERE ur.userId = ?2 AND ?1 LIKE CONCAT(ur.uri, '%')")
    boolean isAccepted(String path, String userId);

    @Query("SELECT ur.uri FROM UserRole ur where ur.userId = ?1")
    List<String> getAccessPaths(String userid);

//    @Query("DELETE UserRole ur WHERE ur.userId = ?1")
//    boolean clearUserRoles(String userId);

    List<UserRole> removeByUserId(String userId);

}


