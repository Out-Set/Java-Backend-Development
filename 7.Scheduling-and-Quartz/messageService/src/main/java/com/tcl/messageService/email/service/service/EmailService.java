package com.tcl.messageService.pankajSir.email.service.service;

import com.tcl.messageService.pankajSir.email.service.domainobject.EmailPayloadMO;

public interface EmailService {
	
	String getServiceTypeCode();
	void sendEmail(EmailPayloadMO emailPayloadMO);
	void sendEmailWithAttachment(EmailPayloadMO emailPayloadMO);
	
}
