package com.savan.bulkRequestProcess.service;

import com.savan.bulkRequestProcess.entity.RequestResponseAudit;
import com.savan.bulkRequestProcess.repo.ReqRespEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReqRespEntityService {

    @Autowired
    private ReqRespEntityRepo reqRespEntityRepo;

    // Save requests
    public String saveRequests(RequestResponseAudit requestResponseAudit){
        reqRespEntityRepo.save(requestResponseAudit);
        return "Record Saved Successfully";
    }

    // Find all by process-status
    public List<RequestResponseAudit> getRequestsByProcessStatus(Boolean processStatus){
        return reqRespEntityRepo.findAllByProcessStatus(processStatus);
    }

    // Find by correlation-id
    public RequestResponseAudit findByCorrelationId(String correlationId){
        return reqRespEntityRepo.findByCorrelationId(correlationId);
    }
}
