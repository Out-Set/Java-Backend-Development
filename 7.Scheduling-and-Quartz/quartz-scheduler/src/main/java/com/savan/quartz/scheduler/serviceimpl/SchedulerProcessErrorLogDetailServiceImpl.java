package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessErrorLogDetail;
import com.savan.quartz.scheduler.repo.SchedulerProcessErrorLogDetailRepo;
import com.savan.quartz.scheduler.service.ISchedulerProcessErrorLogDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulerProcessErrorLogDetailServiceImpl implements ISchedulerProcessErrorLogDetailService {

    @Autowired
    private SchedulerProcessErrorLogDetailRepo schedulerProcessErrorLogDetailRepo;

    @Override
    public SchedulerProcessErrorLogDetail create(SchedulerProcessErrorLogDetail schedulerProcessErrorLogDetail) {
        return schedulerProcessErrorLogDetailRepo.save(schedulerProcessErrorLogDetail);
    }

    @Override
    public SchedulerProcessErrorLogDetail readById(Long id) {
        return schedulerProcessErrorLogDetailRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist"));
    }

    @Override
    public List<SchedulerProcessErrorLogDetail> readAll() {
        return schedulerProcessErrorLogDetailRepo.findAll();
    }

    @Override
    public SchedulerProcessErrorLogDetail update(SchedulerProcessErrorLogDetail schedulerProcessErrorLogDetail) {
        if(schedulerProcessErrorLogDetailRepo.findById(schedulerProcessErrorLogDetail.getId()).isEmpty())
            throw new RecordNotFoundException("Record with id "+schedulerProcessErrorLogDetail.getId()+", doesn't exist");
        return schedulerProcessErrorLogDetailRepo.save(schedulerProcessErrorLogDetail);
    }

    @Override
    public String deleteById(Long id) {
        schedulerProcessErrorLogDetailRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully!";
    }
}
