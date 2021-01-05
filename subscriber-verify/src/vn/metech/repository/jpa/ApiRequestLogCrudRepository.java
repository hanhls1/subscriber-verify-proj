package vn.metech.repository.jpa;


import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.ApiRequestLog;

public interface ApiRequestLogCrudRepository extends CrudRepository<ApiRequestLog, String> {
}
