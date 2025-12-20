package com.savan.jasperReports.jasperReport.service;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

@Service
public class GenerateJasperReports {

    @Value("${jasper.report.upload.directoryName}")
    private String reportDirectoryName;

    // Generate Pdf-Report
    public void exportPdfReport(JasperPrint jasperPrint, String reportFileName, Map<String, String> pdfBase64Map,
                                Map<String, String> storedFilePathMap, Map<String, String> reportFileNameMap) {
        try {
            // ByteArrayOutputStream Object, to get Base64 String of report
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            SimplePdfExporterConfiguration pdfConfig = new SimplePdfExporterConfiguration();
            pdfExporter.setConfiguration(pdfConfig);

            // Export the report
            pdfExporter.exportReport();

            // Convert the output stream to a Base64 string
            byte[] pdfBytes = byteArrayOutputStream.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(pdfBytes);

            // Set to jasperReportDto object
            pdfBase64Map.put("pdfReport", base64String);
            storedFilePathMap.put("pdfReport", reportDirectoryName + reportFileName + ".pdf");
            reportFileNameMap.put("pdfReport", reportFileName + ".pdf");

            // Optionally, write PDF to file
            Files.write(Paths.get(reportDirectoryName + reportFileName + ".pdf"), pdfBytes);


        } catch (Exception e) {
            System.out.println("An Error Occurred, While generating the pdf report!! :" + e.getMessage());
        }
    }

    // Generate Xlsx-Report
    public void exportXlsxReport(JasperPrint jasperPrint, String reportFileName, Map<String, String> pdfBase64Map,
                                 Map<String, String> storedFilePathMap, Map<String, String> reportFileNameMap) {
        try {
            // ByteArrayOutputStream Object, to get Base64 String of report
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            SimpleXlsxReportConfiguration xlsxConfig = new SimpleXlsxReportConfiguration();
            xlsxConfig.setOnePagePerSheet(true);
            xlsxExporter.setConfiguration(xlsxConfig);

            // Export the report
            xlsxExporter.exportReport();

            // Convert the output stream to a Base64 string
            byte[] xlsxBytes = byteArrayOutputStream.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(xlsxBytes);

            // Set to jasperReportDto Maps object
            pdfBase64Map.put("xlsReport", base64String);
            storedFilePathMap.put("xlsReport", reportDirectoryName + reportFileName + ".xls");
            reportFileNameMap.put("xlsReport", reportFileName + ".xls");

            // Optionally, write XLS to file
            Files.write(Paths.get(reportDirectoryName + reportFileName + ".xls"), xlsxBytes);

        } catch (Exception e) {
            System.out.println("An Error Occurred, While generating the xls report!! :" + e.getMessage());
        }
    }

    // Generate Html-Report
    public void exportHtmlReport(JasperPrint jasperPrint, String reportFileName, Map<String, String> pdfBase64Map,
                                 Map<String, String> storedFilePathMap, Map<String, String> reportFileNameMap) {
        try {
            // ByteArrayOutputStream Object, to get Base64 String of report
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            HtmlExporter htmlExporter = new HtmlExporter();
            htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArrayOutputStream));
            SimpleHtmlExporterConfiguration htmlConfig = new SimpleHtmlExporterConfiguration();
            htmlExporter.setConfiguration(htmlConfig);

            // Export the report
            htmlExporter.exportReport();

            // Convert the output stream to a Base64 string
            byte[] htmlBytes = byteArrayOutputStream.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(htmlBytes);

            // Set to jasperReportDto object
            pdfBase64Map.put("htmlReport", base64String);
            storedFilePathMap.put("htmlReport", reportDirectoryName + reportFileName + ".html");
            reportFileNameMap.put("htmlReport", reportFileName + ".html");

            // Optionally, write HTML to file
            Files.write(Paths.get(reportDirectoryName + reportFileName + ".html"), htmlBytes);

        } catch (Exception e) {
            System.out.println("An Error Occurred, While generating the html report!! :" + e.getMessage());
        }
    }

    public void exportCsvReport(JasperPrint jasperPrint, String reportFileName, Map<String, String> pdfBase64Map,
                                Map<String, String> storedFilePathMap, Map<String, String> reportFileNameMap) {
        try {
            // Create CSV exporter
            JRCsvExporter csvExporter = new JRCsvExporter();
            csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));

            // Prepare output stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            csvExporter.setExporterOutput(new SimpleWriterExporterOutput(byteArrayOutputStream));

            // Export the report
            csvExporter.exportReport();

            // Convert the output stream to a Base64 string
            byte[] csvBytes = byteArrayOutputStream.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(csvBytes);

            // Set to jasperReportDto object
            pdfBase64Map.put("csvReport", base64String);
            storedFilePathMap.put("csvReport", reportDirectoryName + reportFileName + ".csv");
            reportFileNameMap.put("csvReport", reportFileName + ".csv");

            // Optionally, write CSV to file
            Files.write(Paths.get(reportDirectoryName + reportFileName + ".csv"), csvBytes);

        } catch (Exception e) {
            System.out.println("An Error Occurred, While generating the CSV report!! :" + e.getMessage());
        }

    }
}

