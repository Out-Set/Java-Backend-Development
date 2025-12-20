package com.savan.aws_s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Services {

    String uploadFile(MultipartFile file);

    List<String> allFiles();

    String preSignUrl(String fileName);

    String getFileUrlByFileName(String fileName);
}
