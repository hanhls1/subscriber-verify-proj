package vn.metech.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.constant.RequestStatus;
import vn.metech.entity.IdRequest;

import java.util.Date;

public interface IdRequestCrudRepository extends CrudRepository<IdRequest, String> {

    @EntityGraph(attributePaths = {"idResponse"})
    @Query("select ir from IdRequest ir where ir.requestStatus = ?1 and (ir.lastFetch is null or ir.lastFetch > ?2)")
    Page<IdRequest> findRequestIncludeResponseBy(RequestStatus requestStatus, Date lastFetch, Pageable pageable);

//    @Query("SELECT COUNT( id.customerRequestId) from IdRequest id WHERE id.customerRequestId = ?1")
//    long countRequestsBy(String customerRequestId);

    long countByCustomerRequestId(String customerRequestId);
}
