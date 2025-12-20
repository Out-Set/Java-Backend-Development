package com.savan.aws_s3.controller;

import com.savan.aws_s3.service.S3ServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/s3/file")
public class S3Controller {

    @Autowired
    private S3ServicesImpl s3Services;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file){
        return ResponseEntity.ok(s3Services.uploadFile(file));
    }

    @GetMapping("/all")
    public List<String> getAllFiles(){
        return s3Services.allFiles();
    }

    @GetMapping("/{fileName}")
    public String getFileUrlByFileNam(@RequestParam String fileName){
        return s3Services.getFileUrlByFileName(fileName);
    }
}
