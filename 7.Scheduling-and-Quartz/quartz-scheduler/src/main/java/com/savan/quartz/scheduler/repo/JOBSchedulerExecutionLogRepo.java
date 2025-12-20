package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JOBSchedulerExecutionLogRepo extends JpaRepository<JOBSchedulerExecutionLog, Long> {

}
