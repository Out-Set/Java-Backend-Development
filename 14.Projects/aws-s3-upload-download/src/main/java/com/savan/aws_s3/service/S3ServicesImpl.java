package com.savan.aws_s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.savan.aws_s3.exceptions.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class S3ServicesImpl implements S3Services {

    @Autowired
    private AmazonS3 client;

    @Value("${cloud.aws.app.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {

        if(file == null){
            throw new FileUploadException("file is mandatory !!");
        }

        String actualFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + actualFileName.substring(actualFileName.lastIndexOf("."));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        try {
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucketName, fileName, String.valueOf(file.getInputStream())));
            return this.preSignUrl(fileName);
        } catch (Exception e) {
            throw new FileUploadException("error in uploading file "+e.getMessage());
        }
    }

    @Override
    public List<String> allFiles() {

        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request().withBucketName(bucketName);

        ListObjectsV2Result listObjectsV2Result = client.listObjectsV2(listObjectsRequest);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();
        List<String> listFileUrls = objectSummaries.stream().map(item->this.preSignUrl(item.getKey())).collect(Collectors.toList());

        return listFileUrls;
    }

    @Override
    public String preSignUrl(String fileName) {

        Date expirationDate = new Date();
        long time = expirationDate.getTime();
        int hour = 2;
        time = time + hour * 60 * 60 * 1000;
        expirationDate.setTime(time);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expirationDate);

        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    @Override
    public String getFileUrlByFileName(String fileName) {

        S3Object object = client.getObject(bucketName, fileName);
        String key = object.getKey();
        String url = preSignUrl(key);
        return url;
    }
}
