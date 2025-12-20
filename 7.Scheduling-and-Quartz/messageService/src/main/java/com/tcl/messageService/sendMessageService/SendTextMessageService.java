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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SendTextMessageService {

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

    //----------------------------------------- for sms --------------------------------------//
    public String sendSms(String taskName) throws MessagingException, TemplateException, IOException {
        CustomCommConfigMst customCommConfigMst = customCommConfigMstService.findByCommTypeNameAndStatus(taskName, "A");
        if (customCommConfigMst != null){
            String communicationType = customCommConfigMst.getCommunicationType();
            String communicationTypeName = taskName;

            if (communicationType.equals("SMS")) {
                System.out.println("Sending sms");
                String templateName = taskName+".txt"; // templateName is same as taskName

                // Prepare then send
                List<String> phoneNumbers = prepareMessageBody(templateName, communicationType, communicationTypeName);
                if(!phoneNumbers.isEmpty()){
                    for(String phoneNumber: phoneNumbers) {
                        sendMessage(phoneNumber, communicationType, "!sent");
                    }
                }
            }
            return "Configuration Found";
        }
        return "No Configuration Found!";
    }

    public List<String> prepareMessageBody(String templateName, String communicationType, String communicationTypeName) throws MessagingException, TemplateException, IOException {

        // Get records with status N(new) from customCommData
        List<CustomCommData> customCommDataList = customCommDataService.findByStatus(communicationType,"N");

        // To Collect all contacts
        List<String> phoneNumbers = new ArrayList<>();

        if(customCommDataList != null){
            for(CustomCommData customCommData: customCommDataList){

                String jsonString = customCommData.getCommData();
                phoneNumbers.add(customCommData.getToCust());

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

                /*
                // Prepare the evaluation context
                final Context ctx = new Context();
                ctx.setVariable("month", month);
                ctx.setVariable("year", year);
                ctx.setVariable("accountNumber", accountNumber);
                ctx.setVariable("days", days);

                // Process the template with the correct path
                String messageBody = templateEngine.process("text/" + templateName, ctx);  // Use + for string concatenation
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

                // Set status of customCommData to O
                customCommData.setStatus("O");
                customCommDataService.update(customCommData);
            }
        }
        return phoneNumbers;
    }

    public void sendMessage(String phoneNum, String communicationType, String status) throws MessagingException {
        List<CustomCommAuditDtl> customCommAuditDtlList = customCommAuditDtlService.findByCommunicationTypeAndStatus(communicationType, status);
        if(customCommAuditDtlList != null){
            for(CustomCommAuditDtl customCommAuditDtl: customCommAuditDtlList){

                // Call Message sending API
                ResponseEntity<String> resp = callMessageAPI(phoneNum, customCommAuditDtl.getMessageBody(), customCommAuditDtl);
                String statusCode = "";
                if (resp != null){
                    statusCode = String.valueOf(resp.getStatusCode().value());
                }
                System.out.println("Send message status code :: "+statusCode);

                // After successful send, prepare audit details
                if(statusCode.startsWith("20") && resp != null){
                    customCommAuditDtl.setResponseBody(resp.getBody());
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

    public ResponseEntity<String> callMessageAPI(String phoneNumber, String message, CustomCommAuditDtl customCommAuditDtl){

        String finalMessage = "SMS_HUBCODE "+message;

        String url = "http://172.17.44.63:8080/BE_SMSHUB/messagereceiver";
        url += "?message=" + finalMessage + "&mobile=" + phoneNumber + "&appMsg=Y";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(message, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            // After processing, prepare audit details
            customCommAuditDtl.setRequestBody(message);
            customCommAuditDtl.setRequestTimeStamp(LocalDateTime.now());
            customCommAuditDtl.setStatus("processed");
            customCommAuditDtlService.update(customCommAuditDtl);

            // Sending the message
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        } catch (Exception e) {
            System.out.println("An Exception Occurred !!");
            System.out.println(e);
        }
        return null;
    }
}