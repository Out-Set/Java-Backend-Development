package com.tcl.messageService.sendMessage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailStructure {

    private String subject;
    private String message;
    private String recipient;
    private String[] ccRecipients;
}
