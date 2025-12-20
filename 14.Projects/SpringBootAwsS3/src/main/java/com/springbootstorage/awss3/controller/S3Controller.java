package com.springbootstorage.awss3.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.springbootstorage.awss3.service.S3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class S3Controller {

    @Autowired
    private S3FileService s3FileService;

    // List all files in the bucket
    @GetMapping("/listAllFiles")
    public String listAllFilesInBucket() {
        List<String> files = s3FileService.listAllObjects(s3FileService.getS3BucketName());
        StringBuilder allFiles = new StringBuilder();
        allFiles.append("****************************************\n");
        allFiles.append("List of Files available in bucket ").append(s3FileService.getS3BucketName());
        allFiles.append("\n****************************************\n");
        for (String filename : files)
            allFiles.append(files).append("\n");
        return allFiles.toString();
    }

    @PostMapping("/upload/{fileToUpload}")
    public PutObjectResult uploadFiletoBucket(@PathVariable String fileToUpload) {
        return s3FileService.uploadObject(s3FileService.getS3BucketName(), fileToUpload, new File(s3FileService.getUPLOAD_PATH()+ fileToUpload));
    }

    @PostMapping("/download/{fileToDownload}")
    public String downloadFileFromBucket(@PathVariable String fileToDownload) {
        return s3FileService.downloadObject(s3FileService.getS3BucketName(), fileToDownload);
    }

    @PostMapping("/delete/{fileToDelete}")
    public String deleteFileFromBucket(@PathVariable String fileToDelete) {
        s3FileService.deleteObject(s3FileService.getS3BucketName(), fileToDelete);
        return fileToDelete + " has been deleted successfully from " + s3FileService.getS3BucketName();
    }
}
