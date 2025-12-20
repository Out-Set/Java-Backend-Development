package com.spring.scheduler.DynamicCron.repository;

import com.spring.scheduler.DynamicCron.entity.CommonLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonLogsRepo extends JpaRepository<CommonLogs, Integer> {

}
