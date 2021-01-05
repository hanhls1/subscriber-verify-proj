package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.dto.response.EmailResponse;
import vn.metech.dto.response.PhoneResponse;
import vn.metech.entity.User;

import java.util.List;

public interface UserCrudRepository extends CrudRepository<User, String> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.partner p WHERE u.username = ?1 OR u.email = ?1")
    User getUserByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.partner p")
    List<User> getActivatedUserWithPartner();

    long countByEmail(String email);

    List<User> findAllByPartnerId(String userId);

    @Query("SELECT u.id FROM User u")
    List<String> getAllUserIds();

    @Query("SELECT new vn.metech.dto.response.EmailResponse(u.id, u.email) FROM User u WHERE u.admin = true ORDER BY u.email")
    List<EmailResponse> getAllUserMailAdmins();

    @Query("SELECT new vn.metech.dto.response.PhoneResponse(u.id, u.phoneNumber) FROM User u WHERE u.admin = true ORDER BY u.phoneNumber")
    List<PhoneResponse> getAllUserPhoneAdmins();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.partner p LEFT JOIN FETCH u.subPartner sp WHERE u.id = ?1")
    User getUserIncludePartnersById(String userId);
}
