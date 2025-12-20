package com.tcl.messageService.service;

import com.tcl.messageService.entity.CustomCommAuditDtl;
import com.tcl.messageService.repository.CustomCommAuditDtlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomCommAuditDtlService {

    @Autowired
    private CustomCommAuditDtlRepo customCommAuditDtlRepo;

    public String create(CustomCommAuditDtl customCommAuditDtl){
        customCommAuditDtlRepo.save(customCommAuditDtl);
        return "Created Successfully";
    }

    public CustomCommAuditDtl readById(int id){
        return customCommAuditDtlRepo.findById(id).get();
    }

    public List<CustomCommAuditDtl> readAll(){
        return customCommAuditDtlRepo.findAll();
    }

    public String update(CustomCommAuditDtl customCommAuditDtl){
        CustomCommAuditDtl existingCustomCommAuditDtl = customCommAuditDtlRepo.findById(customCommAuditDtl.getId()).orElse(null);
        if(existingCustomCommAuditDtl != null){
            existingCustomCommAuditDtl.setCreationTimeStamp(customCommAuditDtl.getCreationTimeStamp());
            existingCustomCommAuditDtl.setStatus(customCommAuditDtl.getStatus());
            existingCustomCommAuditDtl.setRequestBody(customCommAuditDtl.getRequestBody());
            existingCustomCommAuditDtl.setResponseBody(customCommAuditDtl.getResponseBody());
            existingCustomCommAuditDtl.setRequestTimeStamp(customCommAuditDtl.getRequestTimeStamp());
            existingCustomCommAuditDtl.setResponseTimeStamp(customCommAuditDtl.getResponseTimeStamp());
            existingCustomCommAuditDtl.setMessageBody(customCommAuditDtl.getMessageBody());
            existingCustomCommAuditDtl.setCommunicationType(customCommAuditDtl.getCommunicationType());
            existingCustomCommAuditDtl.setCommunicationTypeName(customCommAuditDtl.getCommunicationTypeName());

            customCommAuditDtlRepo.save(existingCustomCommAuditDtl);
            return "Updated Successfully";
        }

        return "Not found";
    }

    public String delete(int id){
        customCommAuditDtlRepo.deleteById(id);
        return "Deleted Successfully";
    }


    // Custom Methods
    public List<CustomCommAuditDtl> findByCommunicationTypeAndStatus(String communicationType, String status){
        return customCommAuditDtlRepo.findByCommunicationTypeAndStatus(communicationType, status);
    }
}
