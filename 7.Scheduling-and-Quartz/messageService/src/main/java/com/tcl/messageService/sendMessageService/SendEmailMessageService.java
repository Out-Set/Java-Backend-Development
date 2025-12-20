package com.tcl.messageService.sendMessageService;

import com.tcl.messageService.entity.CustomCommAuditDtl;
import com.tcl.messageService.entity.CustomCommConfigMst;
import com.tcl.messageService.entity.CustomCommData;
import com.tcl.messageService.repository.EntityManagerRepo;
import com.tcl.messageService.service.CustomCommAuditDtlService;
import com.tcl.messageService.service.CustomCommConfigMstService;
import com.tcl.messageService.service.CustomCommDataService;
import com.tcl.messageService.service.MessageTemplatesService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SendEmailMessageService {

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private CustomCommAuditDtlService customCommAuditDtlService;
    @Autowired
    private CustomCommDataService customCommDataService;
    @Autowired
    private EntityManagerRepo entityManagerRepo;
    @Autowired
    CustomCommConfigMstService customCommConfigMstService;
    @Autowired
    private MessageTemplatesService messageTemplatesService;
    @Autowired
    private ProcessTemplateString processTemplateString;

    //------------------------------------------ for email ------------------------------------------//
    public String sendEmail(String taskName) throws MessagingException, TemplateException, IOException {
        CustomCommConfigMst customCommConfigMst = customCommConfigMstService.findByCommTypeNameAndStatus(taskName, "A");
        if(customCommConfigMst != null){
            String communicationType = customCommConfigMst.getCommunicationType();
            String communicationTypeName = taskName;

            if (communicationType.equals("EMAIL")) {
                System.out.println("Sending email message");
                String templateName = taskName+".txt"; // templateName is same as taskName

                // Prepare then send
                List<String> recipientEmails = prepareEmailBody(templateName, communicationType, communicationTypeName);
                if(!recipientEmails.isEmpty()){
                    for(String recipientEmail: recipientEmails){
                        sendEmail(recipientEmail, communicationType, "not sent");
                    }
                }
            }
            return "Configuration Found";
        }
        return "No Configuration Found!";
    }

    public List<String> prepareEmailBody(String templateName, String communicationType, String communicationTypeName) throws MessagingException, TemplateException, IOException {

        // Get records with status N(new) from customCommData
        List<CustomCommData> customCommDataList = customCommDataService.findByStatus(communicationType,"N");

        // To Collect all contacts
        List<String> recipientEmails = new ArrayList<>();

        if(customCommDataList != null){
            for (CustomCommData customCommData: customCommDataList){
                String jsonString = customCommData.getCommData();
                recipientEmails.add(customCommData.getToCust());

                // Extract key's value from json string stored in customCommData
                String month = entityManagerRepo.extractValueOfKey("month", jsonString);
                String year = entityManagerRepo.extractValueOfKey("year", jsonString);
                String accountNumber = entityManagerRepo.extractValueOfKey("accountNumber", jsonString);
                String days = entityManagerRepo.extractValueOfKey("days", jsonString);

                // Create a data model
                Map<String, Object> data = new HashMap<>();
                data.put("month", month);
                data.put("year", year);
                data.put("accountNumber", accountNumber);
                data.put("days", days);

                // Process the template form messageTemplates table
                String templateString = messageTemplatesService.findTemplateStringByTemplateName(communicationTypeName);
                String messageBody = processTemplateString.process(templateString, data);
                System.out.println("Message body context :: " + messageBody);

                /* Not needed for now
                // By thymeleaf engine: Prepare the evaluation context
                final Context ctx = new Context();
                ctx.setVariable("month", month);
                ctx.setVariable("year", year);
                ctx.setVariable("accountNumber", accountNumber);
                ctx.setVariable("days", days);

                // By thymeleaf engine: Process the template with the correct path
                String messageBody = templateEngine.process("text/" + templateName, ctx);
                System.out.println("Message body context :: " + messageBody);
                */

                // Before sending, prepare audit details
                CustomCommAuditDtl customCommAuditDtl = new CustomCommAuditDtl();
                customCommAuditDtl.setCreationTimeStamp(LocalDateTime.now());
                customCommAuditDtl.setMessageBody(messageBody);
                customCommAuditDtl.setCommunicationType(communicationType);
                customCommAuditDtl.setCommunicationTypeName(communicationTypeName);
                customCommAuditDtl.setStatus("not sent");
                customCommAuditDtlService.create(customCommAuditDtl);

                // Set status of customCommData to O(old)
                customCommData.setStatus("O");
                customCommDataService.update(customCommData);
            }
        }
        return recipientEmails;
    }

    public void sendEmail(String recipientEmail, String communicationType, String status) throws MessagingException {
        List<CustomCommAuditDtl> customCommAuditDtlList = customCommAuditDtlService.findByCommunicationTypeAndStatus(communicationType, status);
        if(customCommAuditDtlList != null){
            for(CustomCommAuditDtl customCommAuditDtl: customCommAuditDtlList){

                // Send Email
                String emailBody = customCommAuditDtl.getMessageBody();
                String resp = processEmail(emailBody, recipientEmail, customCommAuditDtl);

                // After successful send, prepare audit details
                if(resp.equals("Email sent successfully!")){
                    customCommAuditDtl.setResponseBody(resp);
                    customCommAuditDtl.setResponseTimeStamp(LocalDateTime.now());
                    customCommAuditDtl.setStatus("sent");
                    customCommAuditDtlService.update(customCommAuditDtl);
                } else {
                    customCommAuditDtl.setResponseBody("Some error occurred!");
                    customCommAuditDtl.setResponseTimeStamp(LocalDateTime.now());
                    customCommAuditDtl.setStatus("not sent");
                    customCommAuditDtlService.update(customCommAuditDtl);
                }
            }
        }
    }

    public String processEmail(String emailBody, String recipientEmail, CustomCommAuditDtl customCommAuditDtl) throws MessagingException {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject("Example plain text email");
        message.setFrom(fromMail);
        message.setTo(recipientEmail);

        // Set the processed message body as plain text
        message.setText(emailBody, false);  // Set plain text content, not HTML

        // After processing, prepare audit details
        customCommAuditDtl.setRequestBody(emailBody);
        customCommAuditDtl.setRequestTimeStamp(LocalDateTime.now());
        customCommAuditDtl.setStatus("processed");
        customCommAuditDtlService.update(customCommAuditDtl);

        // Send mail
        this.mailSender.send(mimeMessage);

        return "Email sent successfully!";
    }


    // IMP: Other Task For Understanding Purpose, Do not erase
    public void sendMailWithInline (
            final String recipientName, final String recipientEmail, String templateName,final String imageResourceName,
            final byte[] imageBytes, final String imageContentType, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        String mailBody = templateEngine.process("html/".concat(templateName), ctx);
        System.out.println("Mail body context :: " + mailBody);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject("Example HTML email with inline image");
        message.setFrom(fromMail);
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("email-inlineimage.html", ctx);
        message.setText(htmlContent, true); // true = isHtml

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        message.addInline(imageResourceName, imageSource, imageContentType);

        // Send mail
        this.mailSender.send(mimeMessage);
    }
}
