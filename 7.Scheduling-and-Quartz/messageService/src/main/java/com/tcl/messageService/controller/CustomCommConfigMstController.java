package com.tcl.messageService.controller;

import com.tcl.messageService.entity.CustomCommConfigMst;
import com.tcl.messageService.service.CustomCommConfigMstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/messageConfigMst")
public class CustomCommConfigMstController {

    @Autowired
    private CustomCommConfigMstService customCommConfigMstService;

    @PostMapping("/create")
    public String create(@RequestBody CustomCommConfigMst customCommConfigMst){
        return customCommConfigMstService.create(customCommConfigMst);
    }

    @GetMapping("/read/{id}")
    public CustomCommConfigMst readById(@PathVariable int id){
        return customCommConfigMstService.readById(id);
    }

    @GetMapping("/read")
    public List<CustomCommConfigMst> readAll(){
        return customCommConfigMstService.readAll();
    }

    @PostMapping("/update")
    public String update(@RequestBody CustomCommConfigMst customCommConfigMst){
        return customCommConfigMstService.update(customCommConfigMst);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        return customCommConfigMstService.delete(id);
    }

    @PostMapping("/findReqRows")
    public List<CustomCommConfigMst> findReqRows(@RequestParam String communicationType, @RequestParam String communicationTypeName) {
        return customCommConfigMstService.findByCommTypeAndCommTypeNameAndStatus(communicationType, communicationTypeName);
    }
}
