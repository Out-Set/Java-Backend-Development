package com.tcl.messageService.controller;

import com.tcl.messageService.entity.MessageTemplates;
import com.tcl.messageService.service.MessageTemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/messageTemplate")
public class MessageTemplatesController {

    @Autowired
    private MessageTemplatesService messageTemplatesService;

    @PostMapping("/create")
    public String createHtmlTemp(@RequestParam(required = false) String templateFileName,
                                 @RequestParam(required = false) String templateString,
                                 @RequestParam(required = false) MultipartFile template) throws IOException {
        return messageTemplatesService.create(templateFileName, templateString, template);
    }

    @GetMapping("/read/{id}")
    public File downloadFile(@PathVariable int id) throws IOException {
        return messageTemplatesService.readById(id);
    }

    @GetMapping("/read")
    public List<MessageTemplates> readAll(){
        return messageTemplatesService.readAll();
    }

    @PostMapping("/update")
    public String update(@RequestBody MessageTemplates messageTemplates){
        return messageTemplatesService.update(messageTemplates);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) throws IOException {
        return messageTemplatesService.delete(id);
    }
}
