package com.savan.quartz.scheduler.repo;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JOBSchedulerDetailRepo extends JpaRepository<JOBSchedulerDetail, Long> {

    List<JOBSchedulerDetail> findByTenantId(Long tenantId);
    List<JOBSchedulerDetail> findByTriggerName(String triggerName);

    List<JOBSchedulerDetail> findByProcessGroupHeaderIdAndJobName(Long processGroupHeaderId, String jobName);

    List<JOBSchedulerDetail> findByProcessGroupHeaderId(Long var1);

    @Query("select distinct jsem.processGroupDetailId from JOBSchedulerDetail jsd inner join jsd.jobSchedulerExecutionMappingDetails jsem where jsd.tenantId = :tenantId")
    List<Long> getAllMappedProcessIdAcrossAllProcessGroups(Long tenantId);

    JOBSchedulerDetail findByActiveFlagAndJobName(Boolean activeFlag, String jobName);

    List<JOBSchedulerDetail> findByTriggerGroupNameAndTriggerName(String triggerGroupName, String triggerName);

}
