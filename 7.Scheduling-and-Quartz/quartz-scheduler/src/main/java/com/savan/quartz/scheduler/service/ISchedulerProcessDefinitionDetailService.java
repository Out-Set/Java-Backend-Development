package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessDefinitionDetail;
import com.savan.quartz.scheduler.vo.SchedulerProcessDefinitionDetailDto;

import java.util.List;

public interface ISchedulerProcessDefinitionDetailService {

    SchedulerProcessDefinitionDetailDto create(SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail);

    SchedulerProcessDefinitionDetailDto readById(Long id);

    List<SchedulerProcessDefinitionDetailDto> readAll();

    SchedulerProcessDefinitionDetailDto update(SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail);

    String deleteById(Long id);

    SchedulerProcessDefinitionDetailDto mapEntityToDto(SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail);
}
