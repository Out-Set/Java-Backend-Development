package com.savan.jasper.service;

import com.savan.jasper.entity.JasperReportMst;
import com.savan.jasper.entity.ReportDto;
import com.savan.jasper.repository.EntityManagerRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class ReportExportService {

    @Autowired
    private EntityManagerRepo entityManagerRepo;

    @Autowired
    private JasperReportMstService jasperReportMstService;

    public ReportDto exportReport(String reportName, String reportFormat) throws Exception {

        // Path where generated report will be saved
        String path = "C:\\Users\\hp\\Desktop\\# Files\\Jasper-Reports";

        ReportDto reportDto = new ReportDto();

        // Get JasperReportMst Row
        JasperReportMst jasperReportMst = jasperReportMstService.findByReportName(reportName);

        if(jasperReportMst != null) {

            // Get Report Report-Data From Database
            List<Map<String, Object>> reportData = entityManagerRepo.getDataForReport(jasperReportMst.getSqlQuery());

            JasperReport jasperReport = getJasperReport(jasperReportMst);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Savan Prajapati");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Generate Base-64 String of Report and Return ReportDto Object
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if(reportFormat.equalsIgnoreCase("html")) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\students.html");
                JasperExportManager.exportReportToXmlStream(jasperPrint, byteArrayOutputStream);
            }

            if(reportFormat.equalsIgnoreCase("pdf")) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\students.pdf");
                JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
            }

            byte[] bytesArray = byteArrayOutputStream.toByteArray();
            String base64Encoded = Base64.getEncoder().encodeToString(bytesArray);

            // Construct ReportDto Object
            reportDto.setReportData(reportData);
            reportDto.setPdfBase64(base64Encoded);
            reportDto.setStoredFilePath(path);

            byteArrayOutputStream.close();

            return reportDto;

        } else {
            throw new Exception("Record for this report-name not found in the database");
        }
    }

    // Get JasperReport from jrXmlFile
    public JasperReport getJasperReport(JasperReportMst jasperReportMst) throws Exception {
        byte[] fileContent = jasperReportMst.getJrxmlReportFile();
        if(fileContent != null){
            InputStream inputStream = new ByteArrayInputStream(fileContent);
            return JasperCompileManager.compileReport(inputStream);
        } else  {
            throw new Exception("File not found in the database");
        }
    }


    // Original Method
    /*
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\hp\\Desktop\\# Files\\Jasper-Reports";
        List<Student> students = studentService.getAllStudents();
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:students.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Savan Prajapati");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\students.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\students.pdf");
        }

        return "Report generated at path : " + path;
    }
     */
}
