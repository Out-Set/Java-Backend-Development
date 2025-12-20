package com.savan.quartz.comm;

import org.quartz.JobExecutionContext;

import java.util.List;

public interface CommDataService {

    CommData create(CommData commData);

    List<CommData> getUnProcessedRecords();

    void markAsProcessed(CommData commData);

    void markAsUnProcessed(CommData commData);

    List<CommData> getProcessedRecords();

    void sendMail(String toEmail, String subject, String body);

    void sendMail(JobExecutionContext context);
}
