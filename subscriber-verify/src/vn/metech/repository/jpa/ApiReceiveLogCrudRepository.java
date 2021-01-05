package vn.metech.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.ApiReceiveLog;

public interface ApiReceiveLogCrudRepository extends CrudRepository<ApiReceiveLog, String> {
}
