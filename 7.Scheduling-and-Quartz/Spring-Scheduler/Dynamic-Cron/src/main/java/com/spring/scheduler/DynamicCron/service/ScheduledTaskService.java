package com.spring.scheduler.DynamicCron.service;

import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import com.spring.scheduler.DynamicCron.repository.ScheduledTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledTaskService {

    @Autowired
    private ScheduledTaskRepo scheduledTaskRepo;

    public ScheduledTask saveTask(ScheduledTask scheduledTask){
        return scheduledTaskRepo.save(scheduledTask);
    }

    public List<ScheduledTask> getAllTasks(){
        return scheduledTaskRepo.findAll();
    }

    public ScheduledTask findByTaskName(String taskName){
        return scheduledTaskRepo.findByTaskName(taskName);
    }

    public List<String> findTaskNameByTaskType(String taskType){
        return scheduledTaskRepo.findTaskNameByTaskType(taskType);
    }

    public String updateStatusOfProgActive(String taskName){
        ScheduledTask task = findByTaskName(taskName);
        task.setStatus("TRUE");
        scheduledTaskRepo.save(task);
        return "Status Set to TRUE";
    }

    public String updateStatusOfProgNotActive(String taskName){
        ScheduledTask task = findByTaskName(taskName);
        task.setStatus("FALSE");
        scheduledTaskRepo.save(task);
        return "Status Set to FALSE";
    }

    public String updateCronExpression(String cronExpression, String taskName) {
        scheduledTaskRepo.updateCronExpression(cronExpression, taskName);
        return "Updated Successfully";
    }

    public void executeProcedure1(){
        scheduledTaskRepo.executeProcedure1();
    }

    public void executeProcedure2(int id, String name){
        scheduledTaskRepo.executeProcedure2(id, name);
    }

    public List<ScheduledTask> tasksWithStatus(){
        return scheduledTaskRepo.findAll();
    }













    // RDM ENABLE
//    @Procedure(value  = "DBMS_SCHEDULER.ENABLE")
//    void enableJobFSL(String jobName);
//
//    // RDM Disable
//    @Procedure(value  = "DBMS_SCHEDULER.DISABLE")
//    void disableJobFSL(String jobName);

    // ReSchedule FSL-JOBs
//    @Procedure(value = "DBMS_SCHEDULER.SET_ATTRIBUTE")
//    public void reScheduleFSL(String jobName, String attribute, String value);

//    public UserSchedulerJobs findByJobName(@Param("job_name") String jobName);

}
