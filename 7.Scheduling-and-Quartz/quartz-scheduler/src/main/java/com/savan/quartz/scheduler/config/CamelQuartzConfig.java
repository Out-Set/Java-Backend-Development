package com.savan.quartz.scheduler.config;

import org.apache.camel.component.quartz.QuartzComponent;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@Configuration
public class CamelQuartzConfig {

    @Value("${camel.quartz.scheduler.instanceName}")
    private String  instanceName;

    @Value("${camel.quartz.scheduler.jobStore.class}")
    private String  jobStoreClass;

    @Value("${camel.quartz.scheduler.threadPool.class}")
    private String  threadPoolClass;

    @Value("${camel.quartz.scheduler.threadPool.threadCount}")
    private String  threadCount;

    @Value("${camel.quartz.scheduler.threadPool.threadPriority}")
    private String  threadPriority;

    @Bean
    @Primary
    @Qualifier("camelQuartzScheduler")
    public Scheduler camelQuartzScheduler() throws Exception {
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", instanceName);
        props.put("org.quartz.jobStore.class", jobStoreClass);
        props.put("org.quartz.threadPool.class", threadPoolClass);
        props.put("org.quartz.threadPool.threadCount", threadCount);
        props.put("org.quartz.threadPool.threadPriority", threadPriority);

        return new StdSchedulerFactory(props).getScheduler();
    }

    @Bean
    public QuartzComponent quartzComponent(
            @Qualifier("camelQuartzScheduler") Scheduler scheduler) {
        QuartzComponent qc = new QuartzComponent();
        qc.setScheduler(scheduler);
        return qc;
    }
}


