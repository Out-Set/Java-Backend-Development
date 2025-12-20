package com.tcl.messageService.controller;

import com.tcl.messageService.entity.CustomCommAuditDtl;
import com.tcl.messageService.service.CustomCommAuditDtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/audit")
public class CustomCommAuditDtlController {

    @Autowired
    private CustomCommAuditDtlService customCommAuditDtlService;

    @PostMapping("/create")
    public String create(@RequestBody CustomCommAuditDtl customCommAuditDtl){
        return customCommAuditDtlService.create(customCommAuditDtl);
    }

    @GetMapping("/read/{id}")
    public CustomCommAuditDtl readById(@PathVariable int id){
        return customCommAuditDtlService.readById(id);
    }

    @GetMapping("/read")
    public List<CustomCommAuditDtl> readAll(){
        return customCommAuditDtlService.readAll();
    }

    @PostMapping("/update")
    public String update(@RequestBody CustomCommAuditDtl customCommAuditDtl){
        return customCommAuditDtlService.update(customCommAuditDtl);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        return customCommAuditDtlService.delete(id);
    }
}
