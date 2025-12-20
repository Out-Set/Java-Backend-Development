package com.savan.jasper.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReportDto {

    List<Map<String, Object>> reportData;
    private String pdfBase64;
    private String storedFilePath;
}
