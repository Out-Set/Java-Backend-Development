package com.thymeleaf.createPdf.genPdfWithThymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Note: Hit this api on web
@Controller
public class SendMessageController {
    @GetMapping("/message")
    public String getMessage(Model model) {
        model.addAttribute("month", "March");
        model.addAttribute("year", "2024");
        model.addAttribute("accountNumber", "XXXX1404");
        model.addAttribute("daysToUpdate", "10");
        System.out.println("Template is ");
        return "message-template";
    }

    String message = "Dear Sir/Madam, We have initiated update of your credit report for attachMonth, attachYYYY for delayed/non payment of dues against your Tata Capital loan a/c attachAccountNo. It may take upto attachDays days to update in your credit report. Any payment made after the due date will be reflected in the next month's reporting.";
}
