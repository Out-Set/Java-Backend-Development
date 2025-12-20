package com.multiple.db.experianDb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "experian_data")
public class ExperianData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT", name = "experian_xml_data")
    private String experianXmlData;

    @Lob
    @Column(columnDefinition = "TEXT", name = "experian_json_data")
    private String experianJsonData;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "creation_time_stamp")
    private LocalDateTime creationTimeStamp;

    @Column(name = "hit_id")
    private String hitId;

    @Column(name = "allow_consent")
    private String allowConsent;

    @Column(name = "client_name")
    private String clientName;
}
