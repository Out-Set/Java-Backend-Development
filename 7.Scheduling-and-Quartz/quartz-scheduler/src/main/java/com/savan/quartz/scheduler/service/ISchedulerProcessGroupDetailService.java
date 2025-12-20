package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupDetail;
import com.savan.quartz.scheduler.vo.SchedulerProcessGroupDetailDto;

import java.util.List;

public interface ISchedulerProcessGroupDetailService {

    SchedulerProcessGroupDetailDto create(SchedulerProcessGroupDetail schedulerProcessGroupDetail);

    SchedulerProcessGroupDetailDto readById(Long id);

    List<SchedulerProcessGroupDetailDto> readAll();

    SchedulerProcessGroupDetailDto update(SchedulerProcessGroupDetail schedulerProcessGroupDetail);

    String deleteById(Long id);

    List<SchedulerProcessGroupDetailDto> getAllProcessGroupDetail(Long tenantId);

    SchedulerProcessGroupDetailDto getMultiThreadedProfileBasedOnProcessId(Long scaleUpGridProcessTypeId, Long tenantId);

    SchedulerProcessGroupDetailDto mapEntityToDto(SchedulerProcessGroupDetail schedulerProcessGroupDetail);
}
