package vn.metech.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.constant.RequestStatus;
import vn.metech.entity.AdRequest;

import java.util.Date;

public interface AdRequestCrudRepository extends CrudRepository<AdRequest, String> {

    @EntityGraph(attributePaths = {"adResponse"})
    @Query("select ar from AdRequest ar where ar.requestStatus = ?1 and (ar.lastFetch is null or ar.lastFetch > ?2)")
    Page<AdRequest> findRequestIncludeResponseBy(RequestStatus requestStatus, Date lastFetch, Pageable pageable);

//    @Query("select count( ar.customerRequestId) from AdRequest ar where ar.customerRequestId = ?1")
//    long countRequestsBy(String customerRequestAd);

    long countByCustomerRequestId(String customerRequestAd);

}
