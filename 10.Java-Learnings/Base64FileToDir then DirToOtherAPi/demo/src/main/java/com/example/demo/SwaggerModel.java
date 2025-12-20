package com.example.demo;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SwaggerModel {

    private String docType;
    private String leadId;
    private String custType;
    private String custId;
    private String docName;
//    private MultipartFile docFileName;
    private String docFile;
}
