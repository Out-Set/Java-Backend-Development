package com.tcl.messageService.pankajSir.email.service.domainobject;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
public class Attachment  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attachmentFilename;
    private byte[] attachmentFileContent;
    @Accessors(fluent = true)
    private Boolean inlineAttachment;
    private String contentId;
}