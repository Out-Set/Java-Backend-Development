package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessDefinitionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerProcessDefinitionDetailRepo extends JpaRepository<SchedulerProcessDefinitionDetail, Long> {

}
