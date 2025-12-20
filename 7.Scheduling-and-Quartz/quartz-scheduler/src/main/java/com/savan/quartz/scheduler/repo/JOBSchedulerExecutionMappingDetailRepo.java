package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionMappingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JOBSchedulerExecutionMappingDetailRepo extends JpaRepository<JOBSchedulerExecutionMappingDetail, Long> {

}
