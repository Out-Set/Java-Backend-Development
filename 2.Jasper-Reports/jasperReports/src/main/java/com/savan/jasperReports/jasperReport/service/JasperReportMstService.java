package com.savan.jasperReports.jasperReport.service;

import com.savan.jasperReports.jasperReport.entity.JasperReportMst;
import com.savan.jasperReports.jasperReport.repository.JasperReportMstRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class JasperReportMstService {

    @Value("${jasper.jrXml.upload.directoryName}")
    private String jrXmlDirectoryName;

    @Autowired
    private JasperReportMstRepo jasperReportMstRepo;

    @Autowired
    private UploadJrXmlFile uploadJrXmlFile;

    // Create
    // Save .jrxml file into the dir and the required configs(reportType(pdf,html,xlsx,csv...) and reportName) into the table
    public String createJasperReport(String reportTypes, MultipartFile file) throws IOException {

        String jrXmlFileName = file.getOriginalFilename();
        JasperReportMst jasperReportMst = new JasperReportMst();
        jasperReportMst.setDirectoryName(jrXmlDirectoryName);
        if (jrXmlFileName != null){
            int lastDotIndex = jrXmlFileName.lastIndexOf('.');
            String fileWithoutExt = jrXmlFileName.substring(0, lastDotIndex);
            jasperReportMst.setJrXmlFileName(jrXmlFileName);
            jasperReportMst.setReportName(fileWithoutExt);
        }
        jasperReportMst.setReportTypes(reportTypes);
        jasperReportMst.setJrxmlReportFile(file.getBytes());
        jasperReportMstRepo.save(jasperReportMst);

        String resp = uploadJrXmlFile.uploadJrXmlFile(file);

        return "Record Saved and " +resp;
    }

    // Read
    public List<JasperReportMst> getJasperReports(){
        return jasperReportMstRepo.findAll();
    }

    // Update
    public String updateJasperReportById(JasperReportMst jasperReportMst) {
        Integer id = jasperReportMst.getReportId();
        JasperReportMst existingJasperReportMst = jasperReportMstRepo.findById(id).orElse(null);

        if(existingJasperReportMst == null){
            return "Data for this id does not exist!";
        } else {
            jasperReportMstRepo.save(jasperReportMst);
            return "Updated Successfully!";
        }
    }

    // Delete
    public String deleteJasperReportById(Integer id){
        JasperReportMst existingJasperReportMst = jasperReportMstRepo.findById(id).orElse(null);

        if(existingJasperReportMst == null){
            return "Data for this id does not exist!";
        } else {
            jasperReportMstRepo.deleteById(id);
            return "Deleted Successfully!";
        }
    }

    // Find JasperReportMst By Report-Name
    public JasperReportMst findByReportName(String reportName){
        return jasperReportMstRepo.findByReportName(reportName);
    }

}

