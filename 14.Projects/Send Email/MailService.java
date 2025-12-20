package com.tcl.messageService.sendMessage;

import com.tcl.messageService.service.CustomCommAuditDtlService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromMail;


    public void sendMail(MailStructure mailStructure) throws MessagingException {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mailStructure.getRecipient());
        simpleMailMessage.setCc(mailStructure.getCcRecipients());

        mailSender.send(simpleMailMessage);
    }

    // Send Attachment with email
    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@baeldung.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);

        mailSender.send(message);
    }

    public void sendCustomMail(MailStructure mailStructure){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        // Get final template text
        String message = createFinalTemplate(mailStructure.getMessage());

        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(mailStructure.getRecipient());
        simpleMailMessage.setCc(mailStructure.getCcRecipients());

        mailSender.send(simpleMailMessage);
    }

    public String createFinalTemplate(String rawTemplate) {

        Map<String, String> replacements = new HashMap<>();
        replacements.put("{month, YYYY}", "March, 2024");
        replacements.put("{xxxx}", "XXXXXXXX1234");
        replacements.put("xx days", "5 days");

        // Replace
        return replacePlaceholders(rawTemplate, replacements);
    }
    private String replacePlaceholders(String original, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            original = original.replace(entry.getKey(), entry.getValue());
        }
        return original;
    }


    // send mail through scheduler
    public String sendMailThroughScheduler(String finalMessage){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("Regarding TCL Loan");
        simpleMailMessage.setText(finalMessage);
        simpleMailMessage.setTo("savankrp@gmail.com");
        simpleMailMessage.setCc("savan.prajapati@bitsflowtech.com",
                                "learnandcode97@gmail.com");

        mailSender.send(simpleMailMessage);
        return "Email send successfully !!";
    }
}
