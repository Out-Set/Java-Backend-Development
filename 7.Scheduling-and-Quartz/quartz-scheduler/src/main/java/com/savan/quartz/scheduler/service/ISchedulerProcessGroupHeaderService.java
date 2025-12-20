package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupHeader;
import com.savan.quartz.scheduler.vo.SchedulerProcessGroupHeaderDto;

import java.util.List;

public interface ISchedulerProcessGroupHeaderService {

    SchedulerProcessGroupHeaderDto create(SchedulerProcessGroupHeader schedulerProcessGroupHeader);

    SchedulerProcessGroupHeaderDto readById(Long id);

    List<SchedulerProcessGroupHeaderDto> readAll();

    SchedulerProcessGroupHeaderDto update(SchedulerProcessGroupHeader schedulerProcessGroupHeader);

    String deleteById(Long id);

    List<SchedulerProcessGroupHeaderDto> getAllProcessGroupHeader(Long tenantId, List<String> module);

    SchedulerProcessGroupHeaderDto mapEntityToDto(SchedulerProcessGroupHeader schedulerProcessGroupHeader);
}
