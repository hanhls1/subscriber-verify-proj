package vn.metech.repository.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.Message;

import java.util.List;

public interface MessageCrudRepository extends CrudRepository<Message, String> {

    @Query("select max(dateTimeInMilliseconds) from Message where confirmInfo = ?1 and messageType = ?2")
    Long getMaxDateTimeInMillisecondsBy(ConfirmInfo confirmInfo, int messageType);

    @EntityGraph(attributePaths = {"confirmInfo"})
    @Query("from Message where processed = false and confirmInfoId = ?1")
    List<Message> getProcessibleMessageBy(String confirmInfoId);

    @Query("select count(id) from Message where processed = false and confirmInfoId = ?1")
    Long countProcessibleMessageBy(String confirmInfoId);
}
