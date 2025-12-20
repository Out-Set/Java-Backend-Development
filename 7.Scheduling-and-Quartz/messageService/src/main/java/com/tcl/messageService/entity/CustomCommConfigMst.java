package com.tcl.messageService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CustomCommConfigMst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String communicationType;

    @Column(length = 512)
    private String query;

    private String status;
    private String communicationTypeName;
}
