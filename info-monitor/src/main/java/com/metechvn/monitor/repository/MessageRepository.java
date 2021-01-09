package com.metechvn.monitor.repository;

import com.metechvn.monitor.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("select m from Message m where m.id = ( select max (m.id) from Message m where m.id is not null )")
    Message findPhoneNumber();
}
