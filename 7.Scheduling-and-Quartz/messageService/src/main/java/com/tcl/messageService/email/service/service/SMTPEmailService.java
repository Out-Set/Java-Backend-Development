package com.tcl.messageService.pankajSir.email.service.service;
import static org.apache.commons.lang3.BooleanUtils.isNotTrue;

import com.tcl.messageService.pankajSir.email.service.domainobject.EmailPayloadMO;
import com.tcl.messageService.pankajSir.email.service.domainobject.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

//import jakarta.inject.Named;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class SMTPEmailService implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    
    private static final String SERVICE_TYPE = "SMTP";
    private static final String CONTENT_TYPE = "application/octet-stream";

    @Override
    public void sendEmail(EmailPayloadMO emailPayloadMO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailPayloadMO.getToAsArray());
        message.setCc(emailPayloadMO.getCcAsArray());
        message.setBcc(emailPayloadMO.getBccAsArray());
        message.setSubject(emailPayloadMO.getSubject());
        message.setText(emailPayloadMO.getMessage());

        emailSender.send(message);
    }
    
    @Override
    public void sendEmailWithAttachment(EmailPayloadMO emailPayloadMO) {
        Assert.notNull(emailPayloadMO.getTo(), "Recipient email must not be null");
        Assert.notNull(emailPayloadMO.getSubject(), "Email subject must not be null");
        Assert.notNull(emailPayloadMO.getMessage(), "Email text must not be null");
        Assert.notNull(emailPayloadMO.getAttachments(), "Attachment must not be null");

        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailPayloadMO.getToAsArray());
            helper.setCc(emailPayloadMO.getCcAsArray());
            helper.setBcc(emailPayloadMO.getBccAsArray());
            helper.setSubject(emailPayloadMO.getSubject());
            helper.setText(emailPayloadMO.getMessage(), true);
            addAttachments(helper, emailPayloadMO);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to create email message", e);
        }
        emailSender.send(message);
    }

	private void addAttachments(MimeMessageHelper helper, EmailPayloadMO emailPayloadMO) throws MessagingException {
		for(Attachment attachment : emailPayloadMO.getAttachments()) {
			if(isNotTrue(attachment.inlineAttachment())) {
		        helper.addAttachment(attachment.getAttachmentFilename(), new ByteArrayResource(attachment.getAttachmentFileContent()));
			} else {
				helper.addInline(attachment.getContentId(), new ByteArrayResource(attachment.getAttachmentFileContent()), CONTENT_TYPE);
			}
		}
	}

	@Override
	public String getServiceTypeCode() {
		return SERVICE_TYPE;
	}
}

