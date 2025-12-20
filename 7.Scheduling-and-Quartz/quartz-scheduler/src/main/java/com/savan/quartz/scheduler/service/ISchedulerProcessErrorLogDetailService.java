package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.SchedulerProcessErrorLogDetail;

import java.util.List;

public interface ISchedulerProcessErrorLogDetailService {

    SchedulerProcessErrorLogDetail create(SchedulerProcessErrorLogDetail schedulerProcessErrorLogDetail);

    SchedulerProcessErrorLogDetail readById(Long id);

    List<SchedulerProcessErrorLogDetail> readAll();

    SchedulerProcessErrorLogDetail update(SchedulerProcessErrorLogDetail schedulerProcessErrorLogDetail);

    String deleteById(Long id);
}
