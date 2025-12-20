package com.tcl.messageService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MessageTemplates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String templateFileName;

    @Column(length = 512)
    private String templateString;

    private byte[] template;
}
