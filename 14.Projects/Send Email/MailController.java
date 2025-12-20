package com.tcl.messageService.sendMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/email")
    public String sendMail(@RequestBody MailStructure mailStructure){
//        mailService.sendMail(mailStructure);
        mailService.sendCustomMail(mailStructure);
        return "Successfully send the mail.......";
    }

    @GetMapping("/message")
    public String getMessage(Model model) {
        model.addAttribute("month", "March");
        model.addAttribute("year", "2024");
        model.addAttribute("accountNumber", "XXXX1404");
        model.addAttribute("daysToUpdate", "10");
        System.out.println("Template is ");
        return "message-template";
    }
}
