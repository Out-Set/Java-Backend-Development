package com.tcl.messageService.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageRequest {

    private String month;
    private String year;
    private String accountNo;
    private String days;
}
