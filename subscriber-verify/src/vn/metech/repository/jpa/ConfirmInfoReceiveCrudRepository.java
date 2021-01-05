package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.common.MfsStatus;
import vn.metech.entity.ConfirmInfoReceive;

import java.util.List;

public interface ConfirmInfoReceiveCrudRepository extends CrudRepository<ConfirmInfoReceive, String> {

    @EntityGraph(attributePaths = {"confirmInfo", "confirmInfo.params"})
    @Query("select cir from ConfirmInfoReceive cir where cir.status in (?1) and cir.confirmInfo.id in (?2)")
    List<ConfirmInfoReceive> findByStatusAndConfirmInfoIds(List<MfsStatus> statuses, List<String> confirmInfoIds);

    @EntityGraph(attributePaths = {"confirmInfo", "confirmInfo.params"})
    @Query("select cir from ConfirmInfoReceive cir where cir.confirmInfo.id in (?1)")
    List<ConfirmInfoReceive> findByConfirmInfoIds(List<String> confirmInfoIds);

    List<ConfirmInfoReceive> findByRequestId(String requestId);

}
