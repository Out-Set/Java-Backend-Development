package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.JOBSchedulerExecutionMappingDetail;
import com.savan.quartz.scheduler.repo.JOBSchedulerExecutionMappingDetailRepo;
import com.savan.quartz.scheduler.service.IJOBSchedulerExecutionMappingDetailService;
import com.savan.quartz.scheduler.vo.JOBSchedulerExecutionMappingDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JOBSchedulerExecutionMappingDetailServiceImpl implements IJOBSchedulerExecutionMappingDetailService {

    @Autowired
    private JOBSchedulerExecutionMappingDetailRepo jobSchedulerExecutionMappingDetailRepo;

    @Override
    public JOBSchedulerExecutionMappingDetailDto create(JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail) {
        return mapEntityToDto(jobSchedulerExecutionMappingDetailRepo.save(jobSchedulerExecutionMappingDetail));
    }

    @Override
    public JOBSchedulerExecutionMappingDetailDto readById(Long id) {
        return mapEntityToDto(jobSchedulerExecutionMappingDetailRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist")));
    }

    @Override
    public List<JOBSchedulerExecutionMappingDetailDto> readAll() {
        return jobSchedulerExecutionMappingDetailRepo.findAll()
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public JOBSchedulerExecutionMappingDetailDto update(JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail) {
        if(jobSchedulerExecutionMappingDetailRepo.findById(jobSchedulerExecutionMappingDetail.getId()).isEmpty())
            throw new RecordNotFoundException("Record with id "+jobSchedulerExecutionMappingDetail.getId()+", doesn't exist");
        return mapEntityToDto(jobSchedulerExecutionMappingDetailRepo.save(jobSchedulerExecutionMappingDetail));
    }

    @Override
    public String deleteById(Long id) {
        jobSchedulerExecutionMappingDetailRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully!";
    }

    @Override
    public JOBSchedulerExecutionMappingDetailDto mapEntityToDto(JOBSchedulerExecutionMappingDetail jobSchedulerExecutionMappingDetail) {
        return new JOBSchedulerExecutionMappingDetailDto(
                jobSchedulerExecutionMappingDetail.getId(),
                jobSchedulerExecutionMappingDetail.getExecutionSequence(),
                jobSchedulerExecutionMappingDetail.getProcessGroupDetailId(),
                jobSchedulerExecutionMappingDetail.getChainedJobExecutionFaultTreatmentStrategy()
        );
    }
}
