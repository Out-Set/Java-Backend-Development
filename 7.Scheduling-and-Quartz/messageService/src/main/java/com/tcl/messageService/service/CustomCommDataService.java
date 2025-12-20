package com.tcl.messageService.service;

import com.tcl.messageService.entity.CustomCommData;
import com.tcl.messageService.repository.CustomCommDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomCommDataService {

    @Autowired
    private CustomCommDataRepo customCommDataRepo;

    public String create(CustomCommData customCommData){
        customCommDataRepo.save(customCommData);
        return "Created successfully";
    }

    public CustomCommData readById(int id){
        return customCommDataRepo.findById(id).get();
    }

    public List<CustomCommData> readAll(){
        return customCommDataRepo.findAll();
    }

    public String update(CustomCommData customCommData){
        CustomCommData existingCustomCommData = customCommDataRepo.findById(customCommData.getId()).orElse(null);
        if(existingCustomCommData != null){
            existingCustomCommData.setCreationTimeStamp(customCommData.getCreationTimeStamp());
            existingCustomCommData.setStatus(customCommData.getStatus());
            existingCustomCommData.setToCust(customCommData.getToCust());
            existingCustomCommData.setCommunicationType(customCommData.getCommunicationType());
            existingCustomCommData.setCommunicationName(customCommData.getCommunicationName());
            existingCustomCommData.setCommData(customCommData.getCommData());

            customCommDataRepo.save(existingCustomCommData);
            return "Updated successfully";
        }
        return "Not found";
    }

    public String delete(int id){
        customCommDataRepo.deleteById(id);
        return "Deleted successfully";
    }

    public List<CustomCommData> findByStatus(String communicationType, String status){
        return customCommDataRepo.findByStatus(communicationType, status);
    }
}
