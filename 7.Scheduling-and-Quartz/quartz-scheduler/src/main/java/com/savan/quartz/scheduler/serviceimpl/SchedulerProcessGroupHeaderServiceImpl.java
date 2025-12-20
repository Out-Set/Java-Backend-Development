package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupDetail;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupHeader;
import com.savan.quartz.scheduler.repo.SchedulerProcessGroupDetailRepo;
import com.savan.quartz.scheduler.repo.SchedulerProcessGroupHeaderRepo;
import com.savan.quartz.scheduler.service.ISchedulerProcessGroupHeaderService;
import com.savan.quartz.scheduler.vo.SchedulerProcessGroupHeaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulerProcessGroupHeaderServiceImpl implements ISchedulerProcessGroupHeaderService {

    @Autowired
    private SchedulerProcessGroupHeaderRepo schedulerProcessGroupHeaderRepo;

    @Autowired
    private SchedulerProcessGroupDetailRepo schedulerProcessGroupDetailRepo;

    @Override
    public SchedulerProcessGroupHeaderDto create(SchedulerProcessGroupHeader schedulerProcessGroupHeader) {
        if(!schedulerProcessGroupHeader.getSchedulerProcessGroupDetails().isEmpty()){
            List<Long> ids = schedulerProcessGroupHeader.getSchedulerProcessGroupDetails().stream()
                    .map(SchedulerProcessGroupDetail::getId)
                    .collect(Collectors.toList());
            List<SchedulerProcessGroupDetail> schedulerProcessGroupDetails =
                    schedulerProcessGroupDetailRepo.findAllById(ids);
            schedulerProcessGroupHeader.setSchedulerProcessGroupDetails(
                    new HashSet<>(schedulerProcessGroupDetails));
        }
        return mapEntityToDto(schedulerProcessGroupHeaderRepo.save(schedulerProcessGroupHeader));
    }

    @Override
    public SchedulerProcessGroupHeaderDto readById(Long id) {
        return mapEntityToDto(schedulerProcessGroupHeaderRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist")));
    }

    @Override
    public List<SchedulerProcessGroupHeaderDto> readAll() {
        return schedulerProcessGroupHeaderRepo.findAll()
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SchedulerProcessGroupHeaderDto update(SchedulerProcessGroupHeader schedulerProcessGroupHeader) {
        if(schedulerProcessGroupHeaderRepo.findById(schedulerProcessGroupHeader.getId()).isEmpty())
            throw new RecordNotFoundException("Record with id "+schedulerProcessGroupHeader.getId()+", doesn't exist");
        return mapEntityToDto(schedulerProcessGroupHeaderRepo.save(schedulerProcessGroupHeader));
    }

    @Override
    public String deleteById(Long id) {
        schedulerProcessGroupHeaderRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully!";
    }

    @Override
    public List<SchedulerProcessGroupHeaderDto> getAllProcessGroupHeader(Long tenantId, List<String> module) {
        return schedulerProcessGroupHeaderRepo.getAllProcessGroupHeader(tenantId, module)
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SchedulerProcessGroupHeaderDto mapEntityToDto(SchedulerProcessGroupHeader schedulerProcessGroupHeader) {
        return new SchedulerProcessGroupHeaderDto(
                schedulerProcessGroupHeader.getId(),
                schedulerProcessGroupHeader.getProcessGroupDisplayName(),
                schedulerProcessGroupHeader.getProcessGroupDescription(),
                schedulerProcessGroupHeader.getTransactionEvent(),
                schedulerProcessGroupHeader.getModule(),
                schedulerProcessGroupHeader.getExecutionControlState(),
                schedulerProcessGroupHeader.getSchedulerProcessGroupDetails()
                        .stream()
                        .map(SchedulerProcessGroupDetail::getId)
                        .collect(Collectors.toSet())
        );
    }
}
