package com.thymeleaf.createPdf.ghflPdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;

@RestController
@RequestMapping("/ghfl")
public class ghflPdfController {

    @Autowired
    private ghflPdfService ghflPdfService;



    @GetMapping("/get/receipt")
    public ResponseEntity<InputStreamResource> createPdf() throws IOException {

        // Add Data(key-value pairs) below the title
        LinkedHashMap<Object, Object> keyValues = new LinkedHashMap<>();
        keyValues.put("Name", "XYZ");
        keyValues.put("Pan", "AAAAA0000A");
        keyValues.put("Address", "Delhi");
        keyValues.put("Product", "Home Loan");
        keyValues.put("Amount", "5000000");
        keyValues.put("Status", "Under Process");

        ByteArrayInputStream pdf = ghflPdfService.createPdf(keyValues);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline;file=demo.pdf");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
