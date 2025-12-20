package com.savan.bulkRequestProcess.repo;

import com.savan.bulkRequestProcess.entity.RequestResponseAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReqRespEntityRepo extends JpaRepository<RequestResponseAudit, Long> {

    // Find all requests by process status
    List<RequestResponseAudit> findAllByProcessStatus(Boolean processStatus);

    // Find by correlation-id
    RequestResponseAudit findByCorrelationId(String correlationId);
}
