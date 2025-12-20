package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionMappingDetail;
import com.savan.quartz.scheduler.vo.JOBSchedulerExecutionMappingDetailDto;

import java.util.List;

public interface IJOBSchedulerExecutionMappingDetailService {

    JOBSchedulerExecutionMappingDetailDto create(JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail);

    JOBSchedulerExecutionMappingDetailDto readById(Long id);

    List<JOBSchedulerExecutionMappingDetailDto> readAll();

    JOBSchedulerExecutionMappingDetailDto update(JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail);

    String deleteById(Long id);

    JOBSchedulerExecutionMappingDetailDto mapEntityToDto(JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail);
}
