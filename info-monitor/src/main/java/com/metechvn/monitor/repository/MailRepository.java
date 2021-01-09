package com.metechvn.monitor.repository;

import com.metechvn.monitor.entity.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends CrudRepository<Email, Long> {

    @Query("select m from Email m where m.id = ( select max (m.id) from Email m where m.id is not null )")
    Email findMailConfig();
}
