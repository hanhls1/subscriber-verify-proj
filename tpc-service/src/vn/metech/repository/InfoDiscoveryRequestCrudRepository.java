package vn.metech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.constant.RequestStatus;
import vn.metech.entity.InfoDiscoveryRequest;

import java.util.Date;

public interface InfoDiscoveryRequestCrudRepository extends CrudRepository<InfoDiscoveryRequest, String> {

    @EntityGraph(attributePaths = {"idResponse"})
    @Query("select ir from InfoDiscoveryRequest ir where ir.requestStatus = ?1 and (ir.lastFetch is null or ir.lastFetch > ?2)")
    Page<InfoDiscoveryRequest> findRequestIncludeResponseBy(RequestStatus requestStatus, Date lastFetch, Pageable pageable);
}
