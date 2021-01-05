package vn.metech.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.constant.RequestStatus;
import vn.metech.entity.LocationRequest;

import java.util.Date;

public interface LocationRequestCrudRepository extends CrudRepository<LocationRequest, String> {

    @EntityGraph(attributePaths = {"locationResponse"})
    @Query("select ir from LocationRequest ir where ir.requestStatus = ?1 and (ir.lastFetch is null or ir.lastFetch > ?2)")
    Page<LocationRequest> findRequestIncludeResponseBy(RequestStatus requestStatus, Date lastFetch, Pageable pageable);

//    @Query("SELECT COUNT(lr.customerRequestId) FROM LocationRequest lr WHERE lr.customerRequestId = :customerRequestId")
//    long countRequestsBy(String customerRequestId);

    long countByCustomerRequestId(String customerRequestId);
}
