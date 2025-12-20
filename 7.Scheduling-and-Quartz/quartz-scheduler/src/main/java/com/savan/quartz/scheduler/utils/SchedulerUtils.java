package com.savan.quartz.scheduler.utils;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import com.savan.quartz.scheduler.jobs.GenericJOBExecutor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.calendar.HolidayCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

@Slf4j
@Component
public class SchedulerUtils {

    @Autowired
    private SchedulerFactoryBean jobScheduler;

    public JobDetail buildJobDetail(JOBSchedulerDetail jobSchedulerDetail) {
        return JobBuilder.newJob(GenericJOBExecutor.class)
                .withIdentity(jobSchedulerDetail.getJobName(), jobSchedulerDetail.getProcessGroupHeaderId().toString())
                .storeDurably()
                .requestRecovery(false)
                .usingJobData(prepareJobDataForChainedExecution(jobSchedulerDetail))
                .build();
    }

    public JobDataMap prepareJobDataForChainedExecution(JOBSchedulerDetail jobSchedulerDetail) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobSchedulerDetail", jobSchedulerDetail);
        return jobDataMap;
    }

    /*
    Options for CronTrigger:
        withMisfireHandlingInstructionDoNothing() → skip the missed fire and go to next scheduled fire.
        withMisfireHandlingInstructionFireAndProceed() → fire immediately and continue schedule.
    */
    public Trigger prepareCronTrigger(JOBSchedulerDetail jobSchedulerDetail) {
        TriggerBuilder<?> builder = newTrigger()
                .withIdentity(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())
                .withSchedule(cronSchedule(jobSchedulerDetail.getCronExpression())
                        .withMisfireHandlingInstructionFireAndProceed()
                );
        // Attach holiday calendar only if needed
        if (jobSchedulerDetail.getRunOnHoliday()==0) {
            builder.modifiedByCalendar(jobSchedulerDetail.getCalendarId());
        }
        return builder.build();
        /*
        return newTrigger()
                .withIdentity(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName())
                .withSchedule(cronSchedule(jobSchedulerDetail.getCronExpression())
                        .withMisfireHandlingInstructionFireAndProceed())
                .modifiedByCalendar(jobSchedulerDetail.getCalendarId())
                .build();
        */
    }

    public boolean isTriggerAlreadyConfigured(JOBSchedulerDetail jobSchedulerDetail) throws SchedulerException {
        TriggerKey triggerKey = triggerKey(jobSchedulerDetail.getTriggerName(), jobSchedulerDetail.getTriggerGroupName());
        return jobScheduler.getScheduler().checkExists(triggerKey);
    }

    // ----------------------------------------- Calender Holiday ----------------------------------------- //
    private HolidayCalendar getExistingHolidayCalendar(String calendarId) throws SchedulerException {
        Calendar calendar = jobScheduler.getScheduler().getCalendar(calendarId);
        if (calendar == null) return new HolidayCalendar();
        return (HolidayCalendar) calendar;
    }

    public void addHolidayDate(String calendarId, LocalDate date) throws SchedulerException {
        HolidayCalendar calendar = getExistingHolidayCalendar(calendarId);
        java.sql.Date excludedDate = java.sql.Date.valueOf(date);
        if (!calendar.getExcludedDates().contains(excludedDate)) {
            calendar.addExcludedDate(excludedDate);
            log.info("Added new holiday date: {}, into calendar-id: {}", date, calendarId);
        }
        jobScheduler.getScheduler().addCalendar(
                calendarId,
                calendar,
                true,
                true
        );
    }

    public void updateHolidayDate(String calendarId, LocalDate oldDate, LocalDate newDate) throws SchedulerException {
        HolidayCalendar calendar = getExistingHolidayCalendar(calendarId);
        java.sql.Date oldSql = java.sql.Date.valueOf(oldDate);
        java.sql.Date newSql = java.sql.Date.valueOf(newDate);
        calendar.removeExcludedDate(oldSql);
        calendar.addExcludedDate(newSql);
        jobScheduler.getScheduler().addCalendar(
                calendarId,
                calendar,
                true,
                true
        );
        log.info("Updated existing holiday date from: {} to {}, into calendar-id: {}", oldDate, newDate, calendarId);
    }

    public void removeHolidayDate(String calendarId, LocalDate date) throws SchedulerException {
        HolidayCalendar calendar = getExistingHolidayCalendar(calendarId);
        java.sql.Date excludedDate = java.sql.Date.valueOf(date);
        if (calendar.getExcludedDates().contains(excludedDate)) {
            calendar.removeExcludedDate(excludedDate);
            log.info("Removed existing holiday date: {} from calendar-id: {}", date, calendarId);
        }
        jobScheduler.getScheduler().addCalendar(
                calendarId,
                calendar,
                true,
                true
        );
    }

}
