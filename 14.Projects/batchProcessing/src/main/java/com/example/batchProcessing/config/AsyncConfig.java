package com.example.batchProcessing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Higher core pool size to handle more simultaneous tasks
        executor.setCorePoolSize(20);
        // Allow the executor to scale up for heavy loads
        executor.setMaxPoolSize(50);
        // Increase the queue capacity to handle burst loads of tasks
        executor.setQueueCapacity(1000);
        // Set the keep-alive time for threads to reclaim resources faster
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("Async-");
        // Rejection policy to handle cases when the queue is full
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}


