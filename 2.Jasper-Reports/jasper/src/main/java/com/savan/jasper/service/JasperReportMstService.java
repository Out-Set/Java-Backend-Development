package com.savan.jasper.service;

import com.savan.jasper.entity.JasperReportMst;
import com.savan.jasper.repository.JasperReportMstRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class JasperReportMstService {

    @Autowired
    private JasperReportMstRepo jasperReportMstRepo;

    // Create
    public String createJasperReport(String sqlQuery, String reportName, MultipartFile file) throws IOException {

        JasperReportMst jasperReportMst = new JasperReportMst();
        jasperReportMst.setSqlQuery(sqlQuery);
        jasperReportMst.setReportName(reportName);
        jasperReportMst.setJrxmlReportFile(file.getBytes());
        jasperReportMstRepo.save(jasperReportMst);

        return "Record Saved Successfully!";
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

