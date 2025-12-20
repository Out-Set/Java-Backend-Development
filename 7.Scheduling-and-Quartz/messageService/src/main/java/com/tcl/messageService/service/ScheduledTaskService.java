package com.tcl.messageService.service;

import com.tcl.messageService.entity.ScheduledTask;
import com.tcl.messageService.repository.ScheduledTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledTaskService {

    @Autowired
    private ScheduledTaskRepo scheduledTaskRepo;

    public String create(ScheduledTask scheduledTask){
        scheduledTaskRepo.save(scheduledTask);
        return "Created successfully";
    }

    public ScheduledTask readById(int id){
        return scheduledTaskRepo.findById(id).get();
    }

    public List<ScheduledTask> readAll(){
        return scheduledTaskRepo.findAll();
    }

    public String update(int id, ScheduledTask scheduledTask){
        ScheduledTask existingScheduledTask = scheduledTaskRepo.findById(id).orElse(null);
        if(existingScheduledTask != null){
            existingScheduledTask.setTaskType(scheduledTask.getTaskType());
            existingScheduledTask.setTaskName(scheduledTask.getTaskName());
            existingScheduledTask.setTarget(scheduledTask.getTarget());
            existingScheduledTask.setCronExpression(scheduledTask.getCronExpression());
            existingScheduledTask.setArgs(scheduledTask.getArgs());
            existingScheduledTask.setStatus(scheduledTask.getStatus());
            existingScheduledTask.setDb(scheduledTask.getDb());

            scheduledTaskRepo.save(existingScheduledTask);
            return "Updated Successfully";
        }
        return "Not found";
    }

    public String delete(int id){
        scheduledTaskRepo.deleteById(id);
        return "Deleted successfully";
    }

    public ScheduledTask findByTaskName(String taskName){
        return scheduledTaskRepo.findByTaskName(taskName);
    }

    public List<String> findTaskNamesByTaskType(String taskType){
        return scheduledTaskRepo.findTaskNamesByTaskType(taskType);
    }

    public String updateStatusOfProgActive(String taskName){
        ScheduledTask task = findByTaskName(taskName);
        task.setStatus("A"); // A->active
        scheduledTaskRepo.save(task);
        return "Status Set to TRUE";
    }

    public String updateStatusOfProgNotActive(String taskName){
        ScheduledTask task = findByTaskName(taskName);
        task.setStatus("I"); // I->inactive
        scheduledTaskRepo.save(task);
        return "Status Set to FALSE";
    }
    
    public String updateCronExpression(String cronExpression, String taskName) {
    	scheduledTaskRepo.updateCronExpression(cronExpression, taskName);
    	return "Successfully Updated!";
    }
}
