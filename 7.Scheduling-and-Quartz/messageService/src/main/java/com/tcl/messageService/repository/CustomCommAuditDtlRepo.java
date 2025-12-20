package com.tcl.messageService.repository;

import com.tcl.messageService.entity.CustomCommAuditDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCommAuditDtlRepo extends JpaRepository<CustomCommAuditDtl, Integer> {

    @Query(value = "select * from custom_comm_audit_dtl where communication_type=:type and status!=:status", nativeQuery = true)
    List<CustomCommAuditDtl> findByCommunicationTypeAndStatus(@Param("type")String type, @Param("status")String status);
}
