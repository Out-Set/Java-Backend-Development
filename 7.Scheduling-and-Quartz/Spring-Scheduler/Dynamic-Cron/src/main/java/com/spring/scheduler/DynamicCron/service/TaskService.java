package com.spring.scheduler.DynamicCron.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.scheduler.DynamicCron.configuration.ScheduledTaskInfo;
import com.spring.scheduler.DynamicCron.dto.ApiArgs;
import com.spring.scheduler.DynamicCron.dto.Method;
import com.spring.scheduler.DynamicCron.dto.ProcArgs;
import com.spring.scheduler.DynamicCron.entity.CommonLogs;
import com.spring.scheduler.DynamicCron.entity.ScheduledTask;
import com.spring.scheduler.DynamicCron.repository.CommonLogsRepo;
import com.spring.scheduler.DynamicCron.repository.SchedulerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Service
public class TaskService {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @Autowired
    private CommonLogsRepo commonLogsRepo;

    @Autowired
    private SchedulerRepo schedulerRepo;

    private final ScheduledTaskRegistrar taskRegistrar;
    private final Map<String, ScheduledTaskInfo> scheduledTasks;

    @Autowired
    public TaskService(ScheduledTaskRegistrar taskRegistrar, Map<String, ScheduledTaskInfo> scheduledTasks) {
        this.taskRegistrar = taskRegistrar;
        this.scheduledTasks = scheduledTasks;
    }

    // Task-Name will be registered as taskId in scheduledTasks map of spring
    public String startTask(String taskName, String cronExpression, String taskType, ScheduledTask scheduledTask) {
        if (!scheduledTasks.containsKey(taskName)) {
            CronTrigger cronTrigger = new CronTrigger(cronExpression);
            String targetName = scheduledTask.getTargetName();
            String args = scheduledTask.getArgs();

            ScheduledFuture<?> future = Objects.requireNonNull(taskRegistrar.getScheduler()).schedule(
                    () -> {
                        System.out.println("Executing dynamic task for task ID: " + taskName);
                        System.out.println("Target name :: "+targetName);
                        String procExecutionStatus = "FALSE";

                        if(taskType.equals("proc")){
                            if(taskName.equals("proc-1")){
                                // Execute proc
                                scheduledTaskService.executeProcedure1();

//                            for(int i=0; i<10000000; i++){
//                                System.out.println("i :: "+i);
//                            }
                                System.out.println();

                            } else if (taskName.equals("proc-2")) {
                                // Execute proc
                                ObjectMapper objectMapper = new ObjectMapper();
                                ProcArgs procArgs = null;
                                try {
                                    procArgs = objectMapper.readValue(args, ProcArgs.class);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println("UserId :: "+ procArgs.getId()+"ProcArgs Name :: "+ procArgs.getName());
                                scheduledTaskService.executeProcedure2(procArgs.getId(), procArgs.getName());

                                System.out.println();
                            } else if (taskName.equals("proc-3")) {
                                // Execute proc
                                int arg = Integer.valueOf(args);
                                procExecutionStatus = schedulerRepo.executeProcedure3(arg);
                                System.out.println("Execution Status in taskService :: "+procExecutionStatus);

                                System.out.println();
                            }

                        } else if(taskType.equals("api")) {
                            RestTemplate restTemplate = new RestTemplate();
                            ObjectMapper objectMapper = new ObjectMapper();
                            if(taskName.equals("post-api_1")) {
                                List<ApiArgs> apiArgsList = new ArrayList<>();
                                ApiArgs arg1 = new ApiArgs("header", "constant", "user_name", "Savan");
                                ApiArgs arg2 = new ApiArgs("parameter", "query", "date", "select sysdate from dual");

                                apiArgsList.add(arg1);
                                apiArgsList.add(arg2);

                                // Convert the list to a JSON string
                                String argsJson = null;
                                try {
                                    argsJson = objectMapper.writeValueAsString(apiArgsList);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println("After converting into JSON :: "+argsJson);

                                // Form object of ScheduledTask
                                ScheduledTask st = new ScheduledTask();

                                st.setTaskType("api");
                                st.setTaskName("api-3");
                                st.setTargetName("http://localhost:8082/scheduledTask/findByTask");
                                st.setCronExpression("0/5 * * * * *");
                                st.setArgs(argsJson);
                                st.setStatus("FALSE");
                                st.setDb("INTG");

                                String finalJson = null;
                                try {
                                    finalJson = objectMapper.writeValueAsString(st);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }

                                // Post API Call
                                try {
//                                    st = objectMapper.readValue(args, ScheduledTask.class);
                                    st = objectMapper.readValue(finalJson, ScheduledTask.class);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }

//                                HttpHeaders headers = new HttpHeaders();
//                                headers.setContentType(MediaType.APPLICATION_JSON);
//                                HttpEntity<ScheduledTask> requestEntity = new HttpEntity<>(st, headers);
//                                ResponseEntity<String> response = restTemplate.exchange(
//                                        targetName,
//                                        HttpMethod.POST,
//                                        requestEntity,
//                                        String.class
//                                );
//                                if (response.getStatusCode().is2xxSuccessful()) {
//                                    System.out.println("Success! Response body: " + response.getBody());
//                                } else {
//                                    System.out.println("Error: " + response.getStatusCode());
//                                }
                                System.out.println();

                            } else if (taskName.equals("get-api_1")) {
                                // Get API Call
                                String response = restTemplate.getForObject(targetName, String.class);
                                System.out.println("Response: " + response);

                            } else if(taskName.equals("api-4")){
                                List<ApiArgs> apiArgsList;
                                try {
                                    apiArgsList = objectMapper.readValue(args, new TypeReference<List<ApiArgs>>() {});
                                    // Form the api
                                    for (ApiArgs value : apiArgsList) {
                                        if (value.getArgValueType().equals("pathvariable")) {
                                            String api = targetName + "/" + value.getArgValue();
                                            System.out.println(api);

                                            String response = restTemplate.getForObject(api, String.class);
                                            System.out.println("Response of created API :: "+response);
                                        } else {
                                            String api = targetName + "?" + value.getArgKey() +"="+ value.getArgValue();
                                            System.out.println(api);

                                            String response = restTemplate.getForObject(api, String.class);
                                            System.out.println("Response of created API :: "+response);
                                        }
                                    }
                                    
                                } catch (Exception e) {
                                    System.out.println("An Exception Occurred!");
                                    System.out.println(e.getMessage());
                                }
                                System.out.println();
                            }
                        } else {
                            // Method Execution
                            System.out.println("Inside Method Block ...");
                            Method m = new Method();
                            if(taskName.equals("method-1")) {
                                m.oneTo10();
                            } else if(taskName.equals("method-2")) {
                                int n = Integer.parseInt(args);
                                System.out.println("Sum of "+n+" natural numbers :: "+m.sumOfNNum(n));
                                procExecutionStatus = "TRUE";
                            }
                            System.out.println();
                        }

                        // Start and NextStart Time
                        Date startTime = new Date();
                        System.out.println("Start Time: "+startTime);
                        Date nextExecutionTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
                        System.out.println("Next Execution: "+nextExecutionTime);

                        // Create logs and save
                        long count1 = this.commonLogsRepo.count();
                        CommonLogs commonLogs = new CommonLogs();
                        commonLogs.setLogID((int)++ count1);
                        commonLogs.setTaskType(scheduledTask.getTaskType());
                        commonLogs.setTaskName(scheduledTask.getTaskName());
                        commonLogs.setTargetName(scheduledTask.getTargetName());
                        commonLogs.setStartTime(startTime);
                        commonLogs.setNextStartTime(nextExecutionTime);
                        commonLogs.setExecutionStatus(procExecutionStatus);
                        commonLogsRepo.save(commonLogs);

                        System.out.println();

                    },
                    new CronTrigger(cronExpression)
            );

            Date startTime = new Date();
            System.out.println("Start Time: "+startTime);
            Date nextExecutionTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());
            System.out.println("Next Execution: "+nextExecutionTime);
            long count1 = this.commonLogsRepo.count();
            CommonLogs commonLogs = new CommonLogs();
            commonLogs.setLogID((int)++ count1);
            commonLogs.setTaskType(scheduledTask.getTaskType());
            commonLogs.setTaskName(scheduledTask.getTaskName());
            commonLogs.setTargetName(scheduledTask.getTargetName());
            commonLogs.setStartTime(startTime);
            commonLogs.setNextStartTime(nextExecutionTime);
            commonLogs.setExecutionStatus("TO BE EXECUTED");
            commonLogsRepo.save(commonLogs);

            ScheduledTaskInfo taskInfo = new ScheduledTaskInfo(taskName, future, cronExpression);
            scheduledTasks.put(taskName, taskInfo);

            return "Task started successfully for task ID: " + taskName + ", Cron-Expression: " + taskInfo.getCronExpression();
        } else {
            return "Task with ID " + taskName + " is already running. To reschedule first stop it.";
        }
    }

    // Stop Task
    public String stopTask(String taskName) {
        if (scheduledTasks.containsKey(taskName)) {
            ScheduledTaskInfo taskInfo = scheduledTasks.get(taskName);

            taskInfo.getScheduledFuture().cancel(true);
            scheduledTasks.remove(taskName);

            // Set Status to 'FALSE' in DB
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

