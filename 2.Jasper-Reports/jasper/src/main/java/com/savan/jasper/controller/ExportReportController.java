package com.savan.jasper.controller;

import com.savan.jasper.entity.ReportDto;
import com.savan.jasper.service.ReportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exportReport")
public class ExportReportController {

    @Autowired
    private ReportExportService reportExportService;

    @GetMapping("/{reportName}/{reportFormat}")
    public ReportDto exportReport(@PathVariable String reportName, @PathVariable String reportFormat) throws Exception {
        return reportExportService.exportReport(reportName, reportFormat);
    }
}
