package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerProcessGroupDetailRepo extends JpaRepository<SchedulerProcessGroupDetail, Long> {

    @Query("SELECT spgd FROM SchedulerProcessGroupDetail spgd WHERE spgd.tenantId = :tenantId ORDER BY spgd.id")
    List<SchedulerProcessGroupDetail> getAllProcessGroupDetail(Long tenantId);

    @Query("SELECT spgd.concurrentProcessingProfileType FROM SchedulerProcessGroupDetail spgd WHERE spgd.scaleUpGridProcessTypeId = :scaleUpGridProcessTypeId AND spgd.tenantId = :tenantId")
    SchedulerProcessGroupDetail getMultiThreadedProfileBasedOnProcessId(Long scaleUpGridProcessTypeId, Long tenantId);
}
