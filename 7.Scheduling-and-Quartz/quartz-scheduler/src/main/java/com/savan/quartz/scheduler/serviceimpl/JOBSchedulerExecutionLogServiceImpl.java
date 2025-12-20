package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;
import com.savan.quartz.scheduler.repo.JOBSchedulerExecutionLogRepo;
import com.savan.quartz.scheduler.service.IJOBSchedulerExecutionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JOBSchedulerExecutionLogServiceImpl implements IJOBSchedulerExecutionLogService {

    @Autowired
    private JOBSchedulerExecutionLogRepo jobSchedulerExecutionLogRepo;

    @Override
    public JOBSchedulerExecutionLog create(JOBSchedulerExecutionLog jobSchedulerExecutionLog) {
        return jobSchedulerExecutionLogRepo.save(jobSchedulerExecutionLog);
    }

    @Override
    public JOBSchedulerExecutionLog readById(Long id) {
        return jobSchedulerExecutionLogRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist"));
    }

    @Override
    public Slice<JOBSchedulerExecutionLog> read(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return jobSchedulerExecutionLogRepo.findAll(pageable);
    }

    @Override
    public List<JOBSchedulerExecutionLog> readAll() {
        return jobSchedulerExecutionLogRepo.findAll();
    }

    @Override
    public JOBSchedulerExecutionLog update(JOBSchedulerExecutionLog jobSchedulerExecutionLog) {
        if(jobSchedulerExecutionLogRepo.findById(jobSchedulerExecutionLog.getId()).isEmpty())
            throw new RecordNotFoundException("Record with id "+jobSchedulerExecutionLog.getId()+", doesn't exist");
        jobSchedulerExecutionLog.setLastUpdatedTimeStamp(LocalDateTime.now());
        return jobSchedulerExecutionLogRepo.save(jobSchedulerExecutionLog);
    }

    @Override
    public String deleteById(Long id) {
        jobSchedulerExecutionLogRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully!";
    }

    @Override
    public JOBSchedulerExecutionLog findById(Long jobLogId, Class<JOBSchedulerExecutionLog> class1) {
        return null;
    }
}
