package com.stayflow.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ObjectStorageConfig {
  private final ApplicationConfiguration props;

  @Bean
  AmazonS3 amazonS3() {
    AmazonS3Client.builder();
    AmazonS3 amazonS3 = AmazonS3ClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(props.getAccessKey(), props.getSecretKey())))
        .withPathStyleAccessEnabled(true)
        .withEndpointConfiguration(new EndpointConfiguration(props.getEndpoint(), AmazonS3Client.S3_SERVICE_NAME))
        .build();
    return amazonS3;
  }

  @Bean
  String s3BucketName() {
    return props.getBucketName();
  }
}
