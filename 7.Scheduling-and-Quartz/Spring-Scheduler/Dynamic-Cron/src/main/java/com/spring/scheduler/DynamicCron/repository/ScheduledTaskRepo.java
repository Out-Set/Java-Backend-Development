package com.spring.scheduler.DynamicCron.repository;

import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ScheduledTaskRepo extends JpaRepository<ScheduledTask, Integer> {

    // Needed
    @Query(value = "select * from Scheduled_Task where task_name =:task_name", nativeQuery = true)
    ScheduledTask findByTaskName(@Param("task_name")String taskName);

    @Query(value = "update Scheduled_Task set cron_expression =:cron_expression where task_name =:task_name", nativeQuery = true)
    void updateCronExpression(@Param("cron_expression")String cronExpressiopn, @Param("task_name")String taskName);

    @Query(value = "select task_name from Scheduled_Task where task_type =:task_type", nativeQuery = true)
    List<String> findTaskNameByTaskType(@Param("task_type")String task_type);

    @Transactional
    @Modifying
    @Query(value = "call INSERT_CURRENT_TIME()", nativeQuery = true)
    void executeProcedure1();


    @Transactional
    @Modifying
    @Query(value = "call insertuser(:id,:name)", nativeQuery = true)
    void executeProcedure2(@Param("id") int id, @Param("name") String name);

    // create dynamic function by taking target-name from db
}
