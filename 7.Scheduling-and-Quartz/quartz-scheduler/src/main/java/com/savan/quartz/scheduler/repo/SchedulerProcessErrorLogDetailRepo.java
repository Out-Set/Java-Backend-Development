package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessErrorLogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerProcessErrorLogDetailRepo extends JpaRepository<SchedulerProcessErrorLogDetail, Long> {

}
