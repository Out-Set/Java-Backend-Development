package com.savan.quartz.scheduler.serviceimpl;

import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.savan.quartz.scheduler.repo.JOBSchedulerDetailRepo;
import com.savan.quartz.scheduler.service.IJOBSchedulerDetailService;
import com.savan.quartz.scheduler.vo.JOBSchedulerDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JOBSchedulerDetailServiceImpl implements IJOBSchedulerDetailService {

    @Autowired
    private JOBSchedulerDetailRepo jobSchedulerDetailRepo;

    @Override
    public JOBSchedulerDetailVO create(JOBSchedulerDetail jobSchedulerDetail) {
        return mapEntityToDto(jobSchedulerDetailRepo.save(jobSchedulerDetail));
    }

    @Override
    public JOBSchedulerDetailVO readById(Long id) {
        return mapEntityToDto(jobSchedulerDetailRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist")));
    }

    @Override
    public List<JOBSchedulerDetailVO> readAll() {
        return jobSchedulerDetailRepo.findAll()
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<JOBSchedulerDetail> readAllJobs() {
        return jobSchedulerDetailRepo.findAll();
    }

    @Override
    public JOBSchedulerDetailVO update(JOBSchedulerDetail jobSchedulerDetail) {
        if(jobSchedulerDetailRepo.findById(jobSchedulerDetail.getId()).isEmpty())
            throw new RecordNotFoundException("Record with id "+jobSchedulerDetail.getId()+", doesn't exist");
        return mapEntityToDto(jobSchedulerDetailRepo.save(jobSchedulerDetail));
    }

    @Override
    public void updateAll(List<JOBSchedulerDetail> jobSchedulerDetails) {
        jobSchedulerDetailRepo.saveAll(jobSchedulerDetails);
    }

    @Override
    public String deleteById(Long id) {
        jobSchedulerDetailRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully!";
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        jobSchedulerDetailRepo.deleteAllById(ids);
    }

    @Override
    public JOBSchedulerDetailVO mapEntityToDto(JOBSchedulerDetail jobSchedulerDetail) {
        return new JOBSchedulerDetailVO(
                jobSchedulerDetail.getId(),
                jobSchedulerDetail.getJobName(),
                jobSchedulerDetail.getProcessGroupHeaderId().toString(),
                jobSchedulerDetail.getTriggerName(),
                jobSchedulerDetail.getTriggerGroupName(),
                jobSchedulerDetail.isActiveFlag(),
                jobSchedulerDetail.getCronExpression(),
                jobSchedulerDetail.getRunOnHoliday(),
                jobSchedulerDetail.getEnvironment()
        );
    }

    @Override
    public List<JOBSchedulerDetail> findByProcessGroupHeaderIdAndJobName(Long var1, String var2) {
        return jobSchedulerDetailRepo.findByProcessGroupHeaderIdAndJobName(var1, var2);
    }

    @Override
    public List<JOBSchedulerDetailVO> findByTriggerName(String var1) {
        return jobSchedulerDetailRepo.findByTriggerName(var1)
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<JOBSchedulerDetail> findByProcessGroupHeaderId(Long var1) {
        return jobSchedulerDetailRepo.findByProcessGroupHeaderId(var1);
    }

    @Override
    public List<Long> getAllMappedProcessIdAcrossAllProcessGroups(Long tenantId) {
        return jobSchedulerDetailRepo.getAllMappedProcessIdAcrossAllProcessGroups(tenantId);
    }

    @Override
    public JOBSchedulerDetailVO getActiveScheduledJobByJobName(Boolean var1, String var2) {
        return mapEntityToDto(jobSchedulerDetailRepo.findByActiveFlagAndJobName(var1, var2));
    }

    @Override
    public List<JOBSchedulerDetailVO> findByTriggerGroupNameAndTriggerName(String var1, String var2) {
        return jobSchedulerDetailRepo.findByTriggerGroupNameAndTriggerName(var1, var2)
                .stream().parallel()
                .map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<JOBSchedulerDetailVO> getJOBSchedulerDetailVOByJOBGroupAndName(String var1, String var2) {
        return List.of();
    }

    @Override
    public List<JOBSchedulerDetailVO> getJOBSchedulerDetailVOByTriggerGroupAndName(String var1, String var2, List<Integer> var3) {
        return List.of();
    }

}
