package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import vn.metech.entity.StatusInfo;
import org.springframework.data.repository.CrudRepository;
import vn.metech.dto.response.StatusResponse;

import java.util.List;

public interface StatusInfoCrudRepository extends CrudRepository<StatusInfo, String> {

    @Query("select new vn.metech.dto.response.StatusResponse( si.id, si.status)" +
            " from StatusInfo si where si.id is not null group by si.id, si.status ORDER BY si.status")
    List<StatusResponse> getListStatus();
}
