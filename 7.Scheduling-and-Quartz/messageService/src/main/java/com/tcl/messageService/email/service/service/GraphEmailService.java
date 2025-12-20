package com.tcl.messageService.pankajSir.email.service.service;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

import java.util.LinkedList;
import java.util.List;

import com.tcl.messageService.pankajSir.email.service.domainobject.Attachment;
import com.tcl.messageService.pankajSir.email.service.domainobject.EmailPayloadMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.FileAttachment;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.AttachmentCollectionResponse;
import com.microsoft.graph.requests.GraphServiceClient;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GraphEmailService implements EmailService {

	
	@Value("${integration.graph.microsoft.azure.sender}")
	private String sender;
	
	@Autowired
	private GraphServiceClient<Request> graphClient;
    
    private static final String SERVICE_TYPE = "GRAPH";
    private static final String CONTENT_TYPE = "application/octet-stream";

    
    public void sendEmail(EmailPayloadMO emailPayloadMO) {
	
		Message message = getMessageWithBodyAndRecipients(emailPayloadMO);		
		sendMail(message);
		
	}

	@Override
	public void sendEmailWithAttachment(EmailPayloadMO emailPayloadMO) {

		Message message = getMessageWithBodyAndRecipients(emailPayloadMO);
		addAttachement(message, emailPayloadMO);
		sendMail(message);
		
	}

	private Message getMessageWithBodyAndRecipients(EmailPayloadMO emailPayloadMO) {

		Message message = new Message();
		message.subject = emailPayloadMO.getSubject();
		ItemBody body = new ItemBody();
		body.contentType = BodyType.HTML;
		body.content = emailPayloadMO.getMessage();
		message.body = body;

		message.toRecipients = createRecipientDetails(emailPayloadMO.getTo());
		log.info("CC {}", emailPayloadMO.getCc());
		if (isNotEmpty(emailPayloadMO.getCc())) {
			message.ccRecipients = createRecipientDetails(emailPayloadMO.getCc());
		}
		log.info("BCC {}", emailPayloadMO.getBcc());
		if (isNotEmpty(emailPayloadMO.getBcc())) {
			message.bccRecipients = createRecipientDetails(emailPayloadMO.getBcc());
		}
		
		return message;
	}


	private void addAttachement(Message message, EmailPayloadMO emailPayloadMO) {

		LinkedList<com.microsoft.graph.models.Attachment> attachmentsList = new LinkedList<>();
		log.info("Adding Attachments to mail request:");
		for(Attachment attachment : emailPayloadMO.getAttachments()) {
			  FileAttachment fileAttachment = new FileAttachment();
			  fileAttachment.oDataType="#microsoft.graph.fileAttachment";
			  fileAttachment.contentType = CONTENT_TYPE;
			  fileAttachment.name = attachment.getAttachmentFilename();
			  fileAttachment.contentBytes = attachment.getAttachmentFileContent();
			if(isTrue(attachment.inlineAttachment())) {
				  fileAttachment.contentId = attachment.getContentId(); 
				  fileAttachment.isInline=true;
			}
			attachmentsList.add(fileAttachment);
			log.info("Attachments Added:");
		}
		AttachmentCollectionResponse attachmentCollectionResponse = new AttachmentCollectionResponse();
		attachmentCollectionResponse.value = attachmentsList;
		AttachmentCollectionPage attachmentCollectionPage = new AttachmentCollectionPage(attachmentCollectionResponse, null);
		message.attachments = attachmentCollectionPage;
		
	}

	private LinkedList<Recipient> createRecipientDetails(List<String> emails) {

		LinkedList<Recipient> recipientsList = new LinkedList<Recipient>();
		emails.forEach(email -> {
			Recipient recipient = new Recipient();
			EmailAddress emailAddress = new EmailAddress();
			emailAddress.address = email;
			recipient.emailAddress = emailAddress;
			recipientsList.add(recipient);
		});

		return recipientsList;
	}


	private void sendMail(Message message) {
		graphClient.users(sender).sendMail(
			UserSendMailParameterSet.newBuilder().withMessage(message).withSaveToSentItems(true).build())
			.buildRequest().post();
				
	}

	@Override
	public String getServiceTypeCode() {
		return SERVICE_TYPE;
	}

	
}