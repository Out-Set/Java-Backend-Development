package com.tcl.messageService.service;

import com.tcl.messageService.entity.CustomCommConfigMst;
import com.tcl.messageService.repository.CustomCommConfigMstRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomCommConfigMstService {

    @Autowired
    private CustomCommConfigMstRepo customCommConfigMstRepo;

    public String create(CustomCommConfigMst customCommConfigMst){
        customCommConfigMstRepo.save(customCommConfigMst);
        return "Created Successfully";
    }

    public CustomCommConfigMst readById(int id){
        return customCommConfigMstRepo.findById(id).get();
    }

    public List<CustomCommConfigMst> readAll(){
        return customCommConfigMstRepo.findAll();
    }

    public String update(CustomCommConfigMst customCommConfigMst){
        CustomCommConfigMst existingCustomCommConfigMst = customCommConfigMstRepo.findById(customCommConfigMst.getId()).orElse(null);
        if(existingCustomCommConfigMst != null){
            existingCustomCommConfigMst.setCommunicationType(customCommConfigMst.getCommunicationType());
            existingCustomCommConfigMst.setQuery(customCommConfigMst.getQuery());
            existingCustomCommConfigMst.setStatus(customCommConfigMst.getStatus());
            existingCustomCommConfigMst.setCommunicationTypeName(customCommConfigMst.getCommunicationTypeName());

            customCommConfigMstRepo.save(existingCustomCommConfigMst);
            return "Updated Successfully";
        }
        return "Not found";
    }

    public String delete(int id){
        customCommConfigMstRepo.deleteById(id);
        return "Deleted Successfully";
    }

    public CustomCommConfigMst findByCommTypeName(String commTypeName){
        return customCommConfigMstRepo.findByCommTypeName(commTypeName);
    }

    public CustomCommConfigMst findByCommTypeNameAndStatus(String commTypeName, String status){
        return customCommConfigMstRepo.findByCommTypeNameAndStatus(commTypeName, status);
    }

    public List<CustomCommConfigMst> findByCommTypeAndCommTypeNameAndStatus(String communicationType, String communicationTypeName){
        return customCommConfigMstRepo.findByCommTypeAndCommTypeNameAndStatus(communicationType, communicationTypeName, "A");
    }
}
