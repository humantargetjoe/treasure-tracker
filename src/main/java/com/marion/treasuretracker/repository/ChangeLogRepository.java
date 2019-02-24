package com.marion.treasuretracker.repository;

import com.marion.treasuretracker.model.ChangeLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeLogRepository extends CrudRepository<ChangeLog, Integer> {
}
