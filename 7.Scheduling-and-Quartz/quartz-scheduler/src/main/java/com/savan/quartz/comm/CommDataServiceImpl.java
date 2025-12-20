package com.savan.quartz.comm;

import com.savan.quartz.scheduler.domainobject.JOBSchedulerDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static com.savan.quartz.comm.Constants.*;

@Slf4j
@Service
public class CommDataServiceImpl implements CommDataService {

    @Autowired
    private CommDataRepo commDataRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public CommData create(CommData commData) {
        commData.setStatus(NEW);
        commData.setCreationTimeStamp(LocalDateTime.now());
        return commDataRepo.save(commData);
    }

    @Override
    public List<CommData> getUnProcessedRecords() {
        return commDataRepo.findByStatus(NEW);
    }

    @Override
    public void markAsProcessed(CommData commData) {
        commData.setStatus(PROCESSED);
        commData.setResponseTimeStamp(LocalDateTime.now());
        commDataRepo.save(commData);
    }

    @Override
    public void markAsUnProcessed(CommData commData) {
        commData.setStatus(UN_PROCESSED);
        commData.setResponseTimeStamp(LocalDateTime.now());
        commDataRepo.save(commData);
    }

    @Override
    public List<CommData> getProcessedRecords() {
        return commDataRepo.findByStatus(PROCESSED);
    }

    @Override
    public void sendMail(String toEmail, String subject, String body) {
        try {
            String fromEmail = mailProperties.getUsername();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);
            mailSender.send(message);
        } catch (MessagingException me) {
            System.out.println(me.getMessage());
        }
    }

    @Override
    public void sendMail(JobExecutionContext jobExecutionContext) {
        JOBSchedulerDetail jobSchedulerDetail = (JOBSchedulerDetail) jobExecutionContext.getMergedJobDataMap().get("jobSchedulerDetail");
        log.info("Environment: {}", jobSchedulerDetail.getEnvironment());
        log.info("sendMail() executed for trigger: {}", jobExecutionContext.getTrigger().getKey());

        getUnProcessedRecords().stream().parallel().forEach(commData -> {
            commData.setRequestTimeStamp(LocalDateTime.now());
            try {
                String fromEmail = mailProperties.getUsername();
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
                messageHelper.setSubject(commData.getSubject());
                messageHelper.setText(commData.getBody(), true);
                messageHelper.setFrom(fromEmail);
                messageHelper.setTo(commData.getRecipient());
                mailSender.send(message);

                commData.setResponseBody("Email sent successfully!");
                markAsProcessed(commData);
            } catch (Exception e){
                log.error("An error occurred while sending email!");
                commData.setResponseBody(e.getMessage());
                markAsUnProcessed(commData);
            }
        });
    }

}
