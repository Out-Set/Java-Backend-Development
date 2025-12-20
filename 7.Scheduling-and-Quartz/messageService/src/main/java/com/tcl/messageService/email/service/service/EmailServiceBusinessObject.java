package com.tcl.messageService.pankajSir.email.service.service;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.util.HashMap;
import java.util.Map;

import com.tcl.messageService.pankajSir.email.service.domainobject.EmailPayloadMO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.microsoft.graph.http.GraphServiceException;

//import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailServiceBusinessObject {
	
	private EmailService emailService;
	
	private final Map<String, EmailService> emailServiceRegistry = new HashMap<>();
	
	private static final String RESPONSE_OK = "OK";
		
	@Autowired
    public EmailServiceBusinessObject(ApplicationContext applicationContext, @Value("${integration.email.default.service.code}")String serviceCode) {
		setApplicationContext(applicationContext);
		//this.serviceCode = serviceCode;
        this.emailService = emailServiceRegistry.get(serviceCode);
        if (this.emailService == null) {
            log.error("No EmailService found for service code: {}", serviceCode);
        } else {
        	log.info("Registered default EmailService class {} for service type code : -> {}", emailService, serviceCode);
        }
    }
	
	public String sendEmail(EmailPayloadMO emailPayloadMO) {
		return sendEmail(emailService, emailPayloadMO);
	}
	
	public String sendEmail(EmailPayloadMO emailPayloadMO, String serviceTypeCode) {
		EmailService emailService = emailServiceRegistry.get(serviceTypeCode);
		 if (emailService == null) {
	            log.error("Cannot send email. No EmailService found for service code: {}", serviceTypeCode);
	            return "Cannot send email. No EmailService found for service code: " + serviceTypeCode;
	      }
		return sendEmail(emailService, emailPayloadMO);
	}
	
	private String sendEmail(EmailService emailService, EmailPayloadMO emailPayloadMO) {
		try {
			if (isEmpty(emailPayloadMO.getAttachments())) {
				log.info("Sending Normal Text Email.");
				emailService.sendEmail(emailPayloadMO);
			} else {
				log.info("Sending Email with Attachment.");
				emailService.sendEmailWithAttachment(emailPayloadMO);
			}
		} catch (GraphServiceException e) {
			log.error("Error occured: " + e.getMessage());
			return e.getMessage();
			
		}
		return RESPONSE_OK;
		
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, EmailService> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, EmailService.class, true, true);
		for (EmailService emailService : beans.values()) {
			String serviceTypeCode = emailService.getServiceTypeCode();
			String responseServiceClass = AopUtils.getTargetClass(emailService).getName();
			if (StringUtils.isEmpty(serviceTypeCode)) {
				log.error("EmailService Implementation {} doesn't support any service type.Not a valid implementation.So rejected.", responseServiceClass);
			} else {
				EmailService emailServiceAlreadyPresent = (EmailService) emailServiceRegistry.get(serviceTypeCode);
				if (emailServiceAlreadyPresent != null) {
					String responseServiceAlreadyPresentClass = AopUtils.getTargetClass(emailServiceAlreadyPresent).getName();

					log.error("EmailService  for service type code {} already registered with another class {}.So replacing existing implementation {}",
							serviceTypeCode, responseServiceAlreadyPresentClass, responseServiceClass );
				}
				emailServiceRegistry.put(serviceTypeCode, emailService);
				log.info("Registered EmailService class {} for service type code : -> {}", responseServiceClass, serviceTypeCode);
			}
		}
	}

}
