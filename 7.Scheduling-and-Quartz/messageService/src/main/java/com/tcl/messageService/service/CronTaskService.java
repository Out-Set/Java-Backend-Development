package com.tcl.messageService.service;

import com.tcl.messageService.entity.CommonLogs;
import com.tcl.messageService.entity.ScheduledTask;
import com.tcl.messageService.repository.CommonLogsRepo;
import com.tcl.messageService.repository.EntityManagerRepo;
import com.tcl.messageService.schedulerConfiguration.ScheduledTaskInfo;
import com.tcl.messageService.sendMessageService.SendEmailMessageService;
import com.tcl.messageService.sendMessageService.SendTextMessageService;
import com.tcl.messageService.sendMessageService.SendWhatsAppMessageService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.util.*;
import java.util.concurrent.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

@Slf4j
public class CronTaskService {

    @Autowired
    private EntityManagerRepo entityManagerRepo;
    @Autowired
    private CustomCommConfigMstService customCommConfigMstService;
    @Autowired
    private CustomCommAuditDtlService customCommAuditDtlService;
    @Autowired
    private CommonLogsRepo commonLogsRepo;
    @Autowired
    private ScheduledTaskService scheduledTaskService;
    @Autowired
    private SendTextMessageService sendTextMessageService;
    @Autowired
    private SendEmailMessageService sendEmailMessageService;
    @Autowired
    private SendWhatsAppMessageService sendWhatsAppMessageService;


    private final ScheduledTaskRegistrar taskRegistrar;
    private final Map<String, ScheduledTaskInfo> scheduledTasks;

    @Autowired
    public CronTaskService(ScheduledTaskRegistrar taskRegistrar, Map<String, ScheduledTaskInfo> scheduledTasks) {
        this.taskRegistrar = taskRegistrar;
        this.scheduledTasks = scheduledTasks;
    }

    // Task-Name will be registered as taskId in scheduledTasks map of spring
    public String startTask(String taskType, String taskName, String cronExpression, ScheduledTask scheduledTask) {

        if (!scheduledTasks.containsKey(taskName)) {
            CronTrigger cronTrigger = new CronTrigger(cronExpression);

            // Extracting required fields of corresponding taskName
            String args = scheduledTask.getArgs();
            String dbName = scheduledTask.getDb();
            String target = scheduledTask.getTarget();

            ScheduledFuture<?> future = Objects.requireNonNull(taskRegistrar.getScheduler()).schedule(
                    () -> {

                        System.out.println("Executing dynamic task for task ID: " + taskName);

                        if (taskType.equals("method")) {

                            // 1: prepareCommunicationData
                            if (target.equals("prepareCommunicationData()")) {
                                entityManagerRepo.prepareCommunicationData(taskName);
                            }

                            // 2: SEND SMS
                            else if (target.equals("sendSMS()")) {
                                try {
                                    String resp = sendTextMessageService.sendSms(taskName);
                                    System.out.println(resp);
                                    if (resp.equals("No Configuration Found!")) {
                                        stopTask(taskName);
                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            // 3: SEND EMAIL
                            else if (target.equals("sendEmail()")) {
                                try {
                                    String resp = sendEmailMessageService.sendEmail(taskName);
                                    System.out.println(resp);
                                    if (resp.equals("No Configuration Found!")) {
                                        stopTask(taskName);
                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            // 4: SEND WHATSAPP
                            else if (target.equals("sendWhatsApp()")) {
                                try {
                                    String resp = sendWhatsAppMessageService.sendWhatsApp(taskName);
                                    System.out.println(resp);
                                    if (resp.equals("No Configuration Found!")) {
                                        stopTask(taskName);
                                    }
                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        // 4. Test for multiple threads and parallel stream: Working and can be used
                        if (target.equals("test1()")) {
                            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
                            List<Integer> range = IntStream.rangeClosed(0, 100).boxed().toList();
                            List<Future<?>> list = new ArrayList<>();
                            System.out.println("test1(), test Started");
                            range.parallelStream().forEach(a -> {
                                list.add(threadPoolExecutor.submit(() -> {
                                    try {
                                        System.out.println("test1(), Thread Sleep: " + a);
                                        Thread.sleep(10000);
                                        System.out.println("test1(), Thread wake: " + a);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }));
                            });
                            System.out.println("test1(), test finished");

                            try {
                                list.parallelStream().forEach(a -> {
                                    try {
                                        a.get();
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("test1(), Test final finished");
                        }

                        if (target.equals("test2()")) {
                            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
                            List<Integer> range = IntStream.rangeClosed(0, 100).boxed().toList();
                            List<Future<?>> list = new ArrayList<>();
                            System.out.println("test2(), test Started");
                            range.parallelStream().forEach(a -> {
                                list.add(threadPoolExecutor.submit(() -> {
                                    try {
                                        System.out.println("test2(), Thread Sleep: " + a);
                                        Thread.sleep(5000);
                                        System.out.println("test2(), Thread wake: " + a);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }));
                            });
                            System.out.println("test2(), test finished");

                            try {
                                list.parallelStream().forEach(a -> {
                                    try {
                                        a.get();
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    } catch (ExecutionException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("test2(), Test final finished");
                        }

                        // Start and NextStart Time for each execution
                        Date startTime = new Date();
                        System.out.println("Start Time: "+startTime);
                        Date nextExecutionTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
                        System.out.println("Next Execution: "+nextExecutionTime);
                        // Create logs and save
                        long count = this.commonLogsRepo.count();
                        CommonLogs commonLogs = new CommonLogs();
                        commonLogs.setLogID((int)++ count);
                        commonLogs.setTaskType(scheduledTask.getTaskType());
                        commonLogs.setTargetName(scheduledTask.getTarget());
                        commonLogs.setTaskName(scheduledTask.getTaskName());
                        commonLogs.setStartTime(startTime);
                        commonLogs.setNextStartTime(nextExecutionTime);
                        commonLogsRepo.save(commonLogs);
                        System.out.println();
                    },
                    new CronTrigger(cronExpression)
            );

            // Start and NextStart Time for the very first time
            Date startTime = new Date();
            System.out.println("Start Time: "+startTime);
            Date nextExecutionTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
            System.out.println("Next Execution: "+nextExecutionTime);

            long count = this.commonLogsRepo.count();
            CommonLogs commonLogs = new CommonLogs();
            commonLogs.setLogID((int)++ count);
            commonLogs.setTaskType(scheduledTask.getTaskType());
            commonLogs.setTargetName(scheduledTask.getTarget());
            commonLogs.setTaskName(scheduledTask.getTaskName());
            commonLogs.setStartTime(startTime);
            commonLogs.setNextStartTime(nextExecutionTime);
            commonLogsRepo.save(commonLogs);
            System.out.println();

            ScheduledTaskInfo taskInfo = new ScheduledTaskInfo(taskName, future, cronExpression);
            scheduledTasks.put(taskName, taskInfo);

            return "Task started successfully for task ID: " + taskName + ", Cron-Expression: " + taskInfo.getCronExpression();
        } else {
            return "Task with ID " + taskName + " is already running. To reschedule first stop it.";
        }
    }

    // Stop Task, Here taskName is as target
    public String stopTask(String taskName) {
        if (scheduledTasks.containsKey(taskName)) {
            ScheduledTaskInfo taskInfo = scheduledTasks.get(taskName);

            taskInfo.getScheduledFuture().cancel(true);
            scheduledTasks.remove(taskName);

            // #Set task running status to false
            scheduledTaskService.updateStatusOfProgNotActive(taskName);

            return "Task stopped successfully for task ID: " + taskName;
        } else {
            return "Task with ID " + taskName + " is not running.";
        }
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}

