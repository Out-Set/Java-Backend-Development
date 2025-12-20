package com.savan.jasper.repository;

import com.savan.jasper.entity.JasperReportMst;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JasperReportMstRepo extends JpaRepository<JasperReportMst, Integer> {

    @Transactional
    JasperReportMst findByReportName(String reportName);



}
