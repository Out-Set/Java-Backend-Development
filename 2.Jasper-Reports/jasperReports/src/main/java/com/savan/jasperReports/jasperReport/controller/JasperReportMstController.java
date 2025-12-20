package com.savan.jasperReports.jasperReport.controller;

import com.savan.jasperReports.jasperReport.entity.JasperReportMst;
import com.savan.jasperReports.jasperReport.service.JasperReportMstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/jasperReportMst")
public class JasperReportMstController {

    @Autowired
    private JasperReportMstService jasperReportMstService;

    // Create
    @PostMapping("/create")
    public String createJasperReport(
            @RequestPart("reportTypes") String reportTypes,
            @RequestPart("file") MultipartFile file) throws IOException {
        return jasperReportMstService.createJasperReport(reportTypes, file);
    }

    // Read
    @GetMapping("/read")
    public List<JasperReportMst> getJasperReports(){
        return jasperReportMstService.getJasperReports();
    }

    // Update
    @PutMapping("/update")
    public String updateJasperReportById(@RequestBody JasperReportMst jasperReportMst) {
        return jasperReportMstService.updateJasperReportById(jasperReportMst);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public String deleteJasperReportById(@PathVariable Integer id){
        return jasperReportMstService.deleteJasperReportById(id);
    }

}
