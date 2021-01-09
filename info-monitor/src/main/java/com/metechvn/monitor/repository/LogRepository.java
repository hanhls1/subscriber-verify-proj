package com.metechvn.monitor.repository;

import com.metechvn.monitor.entity.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {

    List<Log> findAll();
}
