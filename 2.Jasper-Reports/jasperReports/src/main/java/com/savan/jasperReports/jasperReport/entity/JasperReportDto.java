package com.savan.jasperReports.jasperReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JasperReportDto {

    private List< List<Map<String, Object>> > reportData;
    private Map<String, String> reportFileName;
    private Map<String, String> pdfBase64;
    private Map<String, String> storedFilePath;
    private String message;

}
