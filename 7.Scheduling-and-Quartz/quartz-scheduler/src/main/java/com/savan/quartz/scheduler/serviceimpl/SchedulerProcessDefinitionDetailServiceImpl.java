package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessDefinitionDetail;
import com.savan.quartz.scheduler.repo.SchedulerProcessDefinitionDetailRepo;
import com.savan.quartz.scheduler.service.ISchedulerProcessDefinitionDetailService;
import com.savan.quartz.scheduler.vo.SchedulerProcessDefinitionDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulerProcessDefinitionDetailServiceImpl implements ISchedulerProcessDefinitionDetailService {

    @Autowired
    private SchedulerProcessDefinitionDetailRepo schedulerProcessDefinitionDetailRepo;

    @Override
    public SchedulerProcessDefinitionDetailDto create(SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail) {
        return mapEntityToDto(schedulerProcessDefinitionDetailRepo.save(schedulerProcessDefinitionDetail));
    }

    @Override
    public SchedulerProcessDefinitionDetailDto readById(Long id) {
        return mapEntityToDto(schedulerProcessDefinitionDetailRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist")));
    }

    @Override
    public List<SchedulerProcessDefinitionDetailDto> readAll() {
        return schedulerProcessDefinitionDetailRepo.findAll()
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SchedulerProcessDefinitionDetailDto update(SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail) {
        if(schedulerProcessDefinitionDetailRepo.findById(schedulerProcessDefinitionDetail.getId()).isEmpty())
            throw new RecordNotFoundException("Record with id "+schedulerProcessDefinitionDetail.getId()+", doesn't exist");
        return mapEntityToDto(schedulerProcessDefinitionDetailRepo.save(schedulerProcessDefinitionDetail));
    }

    @Override
    public String deleteById(Long id) {
        schedulerProcessDefinitionDetailRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully!";
    }

    @Override
    public SchedulerProcessDefinitionDetailDto mapEntityToDto(SchedulerProcessDefinitionDetail schedulerProcessDefinitionDetail) {
        return new SchedulerProcessDefinitionDetailDto (
                schedulerProcessDefinitionDetail.getId(),
                schedulerProcessDefinitionDetail.getBeanId(),
                schedulerProcessDefinitionDetail.getClassName(),
                schedulerProcessDefinitionDetail.getMethodToExecute(),
                schedulerProcessDefinitionDetail.getConcurrentProcessingStageTypeId()
        );
    }
}
