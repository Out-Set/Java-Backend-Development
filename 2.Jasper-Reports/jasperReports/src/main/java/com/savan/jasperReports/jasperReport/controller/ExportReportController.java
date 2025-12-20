package com.savan.jasperReports.jasperReport.controller;

import com.savan.jasperReports.jasperReport.entity.JasperReportDto;
import com.savan.jasperReports.jasperReport.service.ReportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/exportReport")
public class ExportReportController {

    @Autowired
    private ReportExportService reportExportService;

    @GetMapping("/{reportName}")
    public JasperReportDto exportReport(@PathVariable String reportName, @RequestBody Map<String, Object> params) throws Exception {
        return reportExportService.exportReport(reportName, params);
    }

}
