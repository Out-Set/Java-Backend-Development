package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.savan.quartz.scheduler.vo.JOBSchedulerDetailVO;

import java.util.List;

public interface IJOBSchedulerDetailService {

    JOBSchedulerDetailVO create(JOBSchedulerDetail jobSchedulerDetail);

    JOBSchedulerDetailVO readById(Long id);

    List<JOBSchedulerDetailVO> readAll();

    List<JOBSchedulerDetail> readAllJobs();

    JOBSchedulerDetailVO update(JOBSchedulerDetail jobSchedulerDetail);

    void updateAll(List<JOBSchedulerDetail> jobSchedulerDetails);

    String deleteById(Long id);

    void deleteAllById(List<Long> ids);

    JOBSchedulerDetailVO mapEntityToDto(JOBSchedulerDetail jobSchedulerDetail);

    List<JOBSchedulerDetail> findByProcessGroupHeaderIdAndJobName(Long var1, String var2);

    List<JOBSchedulerDetailVO> findByTriggerName(String var1);

    List<JOBSchedulerDetail> findByProcessGroupHeaderId(Long var1);

    List<Long> getAllMappedProcessIdAcrossAllProcessGroups(Long tenantId);

    JOBSchedulerDetailVO getActiveScheduledJobByJobName(Boolean var1, String var2);

    List<JOBSchedulerDetailVO> findByTriggerGroupNameAndTriggerName(String var1, String var2);

    List<JOBSchedulerDetailVO> getJOBSchedulerDetailVOByJOBGroupAndName(String var1, String var2);

    List<JOBSchedulerDetailVO> getJOBSchedulerDetailVOByTriggerGroupAndName(String var1, String var2, List<Integer> var3);

}
