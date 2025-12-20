package com.savan.jasperReports.jasperReport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JasperReportMst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;
    private String jrXmlFileName;
    private String directoryName;
    private String reportName;
    private String reportTypes;

    @Lob
    private byte[] jrxmlReportFile;
}
