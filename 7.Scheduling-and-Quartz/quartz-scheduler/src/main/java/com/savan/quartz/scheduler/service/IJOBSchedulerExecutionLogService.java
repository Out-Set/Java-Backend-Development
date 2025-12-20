package com.savan.quartz.scheduler.service;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IJOBSchedulerExecutionLogService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    JOBSchedulerExecutionLog create(JOBSchedulerExecutionLog jobSchedulerExecutionLog);

    JOBSchedulerExecutionLog readById(Long id);

    Slice<JOBSchedulerExecutionLog> read(int pageNumber, int pageSize);

    List<JOBSchedulerExecutionLog> readAll();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    JOBSchedulerExecutionLog update(JOBSchedulerExecutionLog jobSchedulerExecutionLog);

    String deleteById(Long id);

    JOBSchedulerExecutionLog findById(Long jobLogId, Class<JOBSchedulerExecutionLog> class1);
}
