package com.savan.quartz.scheduler.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.savan.quartz.scheduler.listeners.JobListener;
import com.savan.quartz.scheduler.listeners.TriggerListener;
import org.quartz.spi.JobFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {
		
	@Bean
    @ConfigurationProperties("spring.quartz.properties")
    public Properties quartzProperties() {
        return new Properties();
    }
	
	@Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        QuartzJobFactory jobFactory = new QuartzJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
	
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource, @Lazy TriggerListener triggerListener) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        
        // Configure Quartz properties
        /*Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        quartzProperties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
        quartzProperties.setProperty("org.quartz.jobStore.isClustered", "true");
        quartzProperties.setProperty("org.quartz.jobStore.useProperties", "false");
        quartzProperties.setProperty("org.quartz.scheduler.instanceName", name);*/
        schedulerFactory.setJobFactory(jobFactory);
        schedulerFactory.setQuartzProperties(quartzProperties());
        schedulerFactory.setDataSource(dataSource);
        //schedulerFactory.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactory.setOverwriteExistingJobs(false);
        schedulerFactory.setStartupDelay(30);
        //schedulerFactory.setSchedulerListeners(triggerListener());
        schedulerFactory.setGlobalTriggerListeners(triggerListener);
        schedulerFactory.setGlobalJobListeners((org.quartz.JobListener) jobListener());
        
        return schedulerFactory;
    }

	//@Bean public LMSJobListener jobListener() { return new LMSJobListener(); }
    
	@Bean
	public TriggerListener triggerListener() {
		return new TriggerListener();
	}
	
	@Bean
	public JobListener jobListener() {
		return new JobListener();
	}
	 
}
