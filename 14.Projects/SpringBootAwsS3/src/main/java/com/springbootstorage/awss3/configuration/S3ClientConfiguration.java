package com.springbootstorage.awss3.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ClientConfiguration {

    @Value("${access.key.id}")
    @Getter
    private String accessKeyId;

    @Value("${access.key.secret}")
    @Getter
    private String accessKeSecret;

    @Value("${s3.region.name}")
    @Getter
    private String bucketRegion;

    @Bean
    public AmazonS3 getAwsS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeSecret);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(bucketRegion)
                .build();
    }
}
