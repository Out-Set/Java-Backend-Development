package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerProcessGroupHeaderRepo extends JpaRepository<SchedulerProcessGroupHeader, Long> {

    @Query("select DISTINCT(schedulerProcessGroupHeader) from SchedulerProcessGroupHeader schedulerProcessGroupHeader  WHERE schedulerProcessGroupHeader.tenantId =:tenantId and schedulerProcessGroupHeader.module in (:module) order by schedulerProcessGroupHeader.id")
    List<SchedulerProcessGroupHeader> getAllProcessGroupHeader(Long tenantId, List<String> module);
}
