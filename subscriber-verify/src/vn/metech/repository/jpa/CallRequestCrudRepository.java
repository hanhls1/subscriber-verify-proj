package vn.metech.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.constant.RequestStatus;
import vn.metech.entity.CallRequest;

import java.util.Date;

public interface CallRequestCrudRepository extends CrudRepository<CallRequest, String> {

    @EntityGraph(attributePaths = {"callResponse"})
    @Query("select cr from CallRequest cr where cr.requestStatus = ?1 and (cr.lastFetch is null or cr.lastFetch > ?2)")
    Page<CallRequest> findRequestIncludeResponseBy(RequestStatus requestStatus, Date lastFetch, Pageable pageable);

    @Query("SELECT COUNT( cr.customerRequestId) FROM CallRequest cr WHERE cr.deleted = false AND cr.customerRequestId = ?1")
    long countRequestsBy(String customerRequestId);

    long countByCustomerRequestId(String customerRequestId);
}
