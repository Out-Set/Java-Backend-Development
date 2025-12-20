package com.tcl.messageService.repository;

import java.util.List;

import com.tcl.messageService.entity.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledTaskRepo extends JpaRepository<ScheduledTask, Integer> {

	// Needed
    @Query(value = "select * from Scheduled_Task where task_name =:taskName", nativeQuery = true)
    ScheduledTask findByTaskName(@Param("taskName") String taskName);
    
    @Query(value = "update Scheduled_Task set cron_expression =:cronExpression where task_name =:taskName", nativeQuery = true)
    void updateCronExpression(@Param("cronExpression")String cronExpression, @Param("taskName")String taskName);

    @Query(value = "select task_name from Scheduled_Task where task_type =:taskType", nativeQuery = true)
    List<String> findTaskNamesByTaskType(@Param("taskType")String taskType);
    
}

