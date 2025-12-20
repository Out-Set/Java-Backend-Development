package com.tcl.messageService.sendMessageService;

import com.tcl.messageService.entity.CustomCommConfigMst;
import com.tcl.messageService.repository.EntityManagerRepo;
import com.tcl.messageService.service.CustomCommAuditDtlService;
import com.tcl.messageService.service.CustomCommConfigMstService;
import com.tcl.messageService.service.CustomCommDataService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.List;

@Service
public class SendWhatsAppMessageService {

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

    //--------------------------------------- for whatsApp ---------------------------------------//
    public String sendWhatsApp(String taskName) throws MessagingException {
        CustomCommConfigMst customCommConfigMst = customCommConfigMstService.findByCommTypeNameAndStatus(taskName, "A");
        if (customCommConfigMst != null){
            String communicationType = customCommConfigMst.getCommunicationType();
            String communicationTypeName = taskName;

            if (communicationType.equals("WHATSAPP")) {
                System.out.println("Sending WhatsApp message");
                String templateName = taskName+".txt"; // templateName is same as taskName

                // Prepare then send
                /*
                List<String> phoneNumbers = prepareMessageBody(templateName, communicationType, communicationTypeName);
                if(!phoneNumbers.isEmpty()){
                    for(String phoneNumber: phoneNumbers) {
                        sendMessage(phoneNumber, communicationType, "!sent");
                    }
                }
                */
            }
            return "Configuration Found";
        }
        return "No Configuration Found!";
    }

    // WhatsApp messaging service is to be implemented in future when required
}
