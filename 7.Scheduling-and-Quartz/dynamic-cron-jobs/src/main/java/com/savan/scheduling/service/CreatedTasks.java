package com.savan.scheduling.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CreatedTasks {

    public void executeTask1(String taskName) {
        if (taskName.equals("test1")) {
            System.out.println(taskName + ", task started");

            ExecutorService taskExecutor = Executors.newFixedThreadPool(5);
            List<Future<?>> futures = IntStream.rangeClosed(0, 100).mapToObj(i ->
                    taskExecutor.submit(() -> {
                        try {
                            System.out.println(taskName + ", Sleeping: " + i);
                            Thread.sleep(10000);
                            System.out.println(taskName + ", Awake: " + i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    })
            ).collect(Collectors.toList());

            // Wait for completion
            futures.forEach(f -> {
                try {
                    f.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            taskExecutor.shutdown();
            System.out.println(taskName + ", task finished");
        }
    }

    public void executeTask2(String taskName) {
        if (taskName.equals("test2")) {
            System.out.println(taskName + ", task started");

            ExecutorService taskExecutor = Executors.newFixedThreadPool(5);
            List<Future<?>> futures = IntStream.rangeClosed(0, 100).mapToObj(i ->
                    taskExecutor.submit(() -> {
                        try {
                            System.out.println(taskName + ", Sleeping: " + i);
                            Thread.sleep(5000);
                            System.out.println(taskName + ", Awake: " + i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    })
            ).collect(Collectors.toList());

            // Wait for completion
            futures.forEach(f -> {
                try {
                    f.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            taskExecutor.shutdown();
            System.out.println(taskName + ", task finished");
        }
    }
}
