package com.savan.jasperReports.jasperReport.service;

import com.savan.jasperReports.jasperReport.entity.JasperReportDto;
import com.savan.jasperReports.jasperReport.entity.JasperReportMst;
import com.savan.jasperReports.jasperReport.repository.EntityManagerRepo;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.util.*;

@Service
public class ReportExportService {

    @Value("${jasper.jrXml.upload.directoryName}")
    private String jrXmlDirectoryName;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JasperReportMstService jasperReportMstService;

    @Autowired
    private EntityManagerRepo entityManagerRepo;

    @Autowired
    private GenerateJasperReports generateJasperReports;

    @Autowired
    private ExtractSqlQuery extractSqlQuery;

    public JasperReportDto exportReport(String reportName, Map<String, Object> params) {

        // JasperReportDto, To Return Final output
        JasperReportDto jasperReportDto = new JasperReportDto();

        try {

            // Get JasperReportMst Row
            JasperReportMst jasperReportMst = jasperReportMstService.findByReportName(reportName);

            // In-case you need to load .jrXml from database
            /*
            byte[] byteArray = jasperReportMst.getJrxmlReportFile();

            // Compile the report from the InputStream
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            */

            // Pick .jrXml from Pre-Defined Directory
            String jrXmlFilePath = "";
            try{
                File jrXmlFile = new File(jrXmlDirectoryName, reportName+".jrxml");
                if (!jrXmlFile.exists()) {
                    System.out.println("File not found: " + jrXmlFile.getAbsolutePath());
                } else {
                    jrXmlFilePath = jrXmlFile.getAbsolutePath();
                    System.out.println("File found: " + jrXmlFilePath);
                }
            } catch (Exception e){
                System.out.println("Configured JrXml File Not Found in this Directory: "+jrXmlDirectoryName);
                jasperReportDto.setMessage("Configured JrXml File Not Found in this Directory"+jrXmlDirectoryName);
            }

            InputStream inputStream = new FileInputStream(jrXmlFilePath);
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Get DB Connection
            Connection dbConnection = dataSource.getConnection();

            // Extract Final SQL-Queries from .jrxml file and get data from db
            List< List<Map<String, Object>> > finalReportDataList = new ArrayList<>();
            Set<String> sqlQuerySet = extractSqlQuery.extractSqlQueryFromJrXml(jasperReport, params);
            for (String sqlQueryString: sqlQuerySet){
                // Get Data from sqlQueryString and add into JasperReportDto object
                System.out.println("SQl Query String: "+sqlQueryString);
                finalReportDataList.add(entityManagerRepo.getReportData(sqlQueryString));
            }
            jasperReportDto.setReportData(finalReportDataList);

            // Fill the report and set params
            Map<String, Object> parameters = new HashMap<>(params);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dbConnection);

            // For final output
            Map<String, String> reportFileNameMap = new HashMap<>();
            Map<String, String> pdfBase64Map = new HashMap<>();
            Map<String, String> storedFilePathMap = new HashMap<>();

            // Extract id parameter
            String id = "";
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if(entry.getKey().equalsIgnoreCase("id")){
                    id = entry.getValue().toString();
                }
            }
            // Prepare Report-File-Name
            String reportFileName = id + "_" + reportName;

            // Get Report-Formats and Generate Reports
            List<String> reportFormats = List.of(jasperReportMst.getReportTypes().split(", "));
            for (String reportFormat: reportFormats){

                System.out.println("Report-Formats: "+reportFormat);

                // Export the report to PDF
                if(reportFormat.equalsIgnoreCase("pdf")){
                    System.out.println("Generating pdf report");
                    generateJasperReports.exportPdfReport(jasperPrint, reportFileName,
                            pdfBase64Map, storedFilePathMap, reportFileNameMap);
                }

                // Export the report to XLS
                if(reportFormat.equalsIgnoreCase("xls") || reportFormat.equalsIgnoreCase("xlsx")){
                    System.out.println("Generating xls report");
                    generateJasperReports.exportXlsxReport(jasperPrint, reportFileName,
                            pdfBase64Map, storedFilePathMap, reportFileNameMap);
                }

                // Export the report to HTML
                if(reportFormat.equalsIgnoreCase("html")){
                    System.out.println("Generating html report");
                    generateJasperReports.exportHtmlReport(jasperPrint, reportFileName,
                            pdfBase64Map, storedFilePathMap, reportFileNameMap);
                }

                // Export the report to CSV
                if(reportFormat.equalsIgnoreCase("csv")){
                    System.out.println("Generating csv report");
                    generateJasperReports.exportCsvReport(jasperPrint, reportFileName,
                            pdfBase64Map, storedFilePathMap, reportFileNameMap);
                }
            }

            // Setting values into jasperReportDto
            jasperReportDto.setPdfBase64(pdfBase64Map);
            jasperReportDto.setReportFileName(reportFileNameMap);
            jasperReportDto.setStoredFilePath(storedFilePathMap);
            jasperReportDto.setMessage("Report generated successfully");

            return jasperReportDto;

        } catch (Exception e) {
            System.out.println("An Error Occurred, While generating the report!! :" + e.getMessage());
            jasperReportDto.setMessage("An Error Occurred, While generating the report!! :" + e.getMessage());
        }

        return jasperReportDto;
    }
}




