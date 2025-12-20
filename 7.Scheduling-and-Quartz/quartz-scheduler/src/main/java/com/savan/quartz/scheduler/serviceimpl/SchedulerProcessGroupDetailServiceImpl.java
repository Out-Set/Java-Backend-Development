package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessDefinitionDetail;
import com.savan.quartz.scheduler.domainobject.SchedulerProcessGroupDetail;
import com.savan.quartz.scheduler.repo.SchedulerProcessDefinitionDetailRepo;
import com.savan.quartz.scheduler.repo.SchedulerProcessGroupDetailRepo;
import com.savan.quartz.scheduler.service.ISchedulerProcessGroupDetailService;
import com.savan.quartz.scheduler.vo.SchedulerProcessGroupDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulerProcessGroupDetailServiceImpl implements ISchedulerProcessGroupDetailService {

    @Autowired
    private SchedulerProcessGroupDetailRepo schedulerProcessGroupDetailRepo;

    @Autowired
    private SchedulerProcessDefinitionDetailRepo schedulerProcessDefinitionDetailRepo;

    @Override
    public SchedulerProcessGroupDetailDto create(SchedulerProcessGroupDetail schedulerProcessGroupDetail) {
        if(!schedulerProcessGroupDetail.getSchedulerProcessDefinitionDetails().isEmpty()) {
            List<Long> ids = schedulerProcessGroupDetail.getSchedulerProcessDefinitionDetails().stream()
                    .map(SchedulerProcessDefinitionDetail::getId)
                    .collect(Collectors.toList());
            List<SchedulerProcessDefinitionDetail> schedulerProcessDefinitionDetails =
                    schedulerProcessDefinitionDetailRepo.findAllById(ids);
            schedulerProcessGroupDetail.setSchedulerProcessDefinitionDetails(schedulerProcessDefinitionDetails);
        }
        return mapEntityToDto(schedulerProcessGroupDetailRepo.save(schedulerProcessGroupDetail));
    }

    @Override
    public SchedulerProcessGroupDetailDto readById(Long id) {
        return mapEntityToDto(schedulerProcessGroupDetailRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist")));
    }

    @Override
    public List<SchedulerProcessGroupDetailDto> readAll() {
        return schedulerProcessGroupDetailRepo.findAll()
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SchedulerProcessGroupDetailDto update(SchedulerProcessGroupDetail schedulerProcessGroupDetail) {
        if(schedulerProcessGroupDetailRepo.findById(schedulerProcessGroupDetail.getId()).isEmpty())
            throw new RecordNotFoundException("Record with id "+schedulerProcessGroupDetail.getId()+", doesn't exist");
        return mapEntityToDto(schedulerProcessGroupDetailRepo.save(schedulerProcessGroupDetail));
    }

    @Override
    public String deleteById(Long id) {
        schedulerProcessGroupDetailRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully!";
    }

    @Override
    public List<SchedulerProcessGroupDetailDto> getAllProcessGroupDetail(Long tenantId) {
        return schedulerProcessGroupDetailRepo.getAllProcessGroupDetail(tenantId)
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SchedulerProcessGroupDetailDto getMultiThreadedProfileBasedOnProcessId(Long scaleUpGridProcessTypeId, Long tenantId) {
        return mapEntityToDto(schedulerProcessGroupDetailRepo.getMultiThreadedProfileBasedOnProcessId(scaleUpGridProcessTypeId, tenantId));
    }

    @Override
    public SchedulerProcessGroupDetailDto mapEntityToDto(SchedulerProcessGroupDetail schedulerProcessGroupDetail) {
        return new SchedulerProcessGroupDetailDto(
                schedulerProcessGroupDetail.getId(),
                schedulerProcessGroupDetail.getProcessDisplayName(),
                schedulerProcessGroupDetail.getProcessDescription(),
                schedulerProcessGroupDetail.getMaximumExecutionFrequency(),
                schedulerProcessGroupDetail.getMaintainExecutionLog(),
                schedulerProcessGroupDetail.getStreamBased(),
                schedulerProcessGroupDetail.getRunInParallel(),
                schedulerProcessGroupDetail.getChainedJobExecutionFaultTreatmentStrategy(),
                schedulerProcessGroupDetail.getMultiThreaded(),
                schedulerProcessGroupDetail.getConcurrentProcessingProfileTypeId(),
                schedulerProcessGroupDetail.getScaleUpGridProcessTypeId(),
                schedulerProcessGroupDetail.getTransactionEvent(),
                schedulerProcessGroupDetail.getSchedulerProcessDefinitionDetails()
                        .stream()
                        .map(SchedulerProcessDefinitionDetail::getId)
                        .collect(Collectors.toList())
        );
    }
}
