package com.tcl.messageService.pankajSir.email.service.domainobject;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailPayloadMO  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> to;
    private List<String> cc;
    private List<String> bcc;
	private String referenceNumber;
    private String templateName;
    private String subject;
    private String message;
    private List<Attachment> attachments;
    private Map<String, Object> additionalData;
    
    public void setTo(String... to) {
    	this.to = List.of(to);
    }
    public String[] getToAsArray() {
    	return isNotEmpty(to)?to.stream().toArray(String[]::new):EMPTY_STRING_ARRAY;
    }
    
    public String[] getCcAsArray() {
    	return isNotEmpty(cc)?cc.stream().toArray(String[]::new):EMPTY_STRING_ARRAY;
    }
    
    public String[] getBccAsArray() {
    	return isNotEmpty(bcc)?bcc.stream().toArray(String[]::new):EMPTY_STRING_ARRAY;
    }
}