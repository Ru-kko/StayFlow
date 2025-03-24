package com.stayflow.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.stayflow.application.port.out.ApplicationVariables;

import lombok.Getter;

@Getter
@Configuration
@ConfigurationProperties
public class ApplicationConfiguration implements ApplicationVariables {
  @Value("${app.page-size}")
  public Integer pageSize;
  @Value("${app.bucketName}")
  private String bucketName;

  @Value("${minio.endpoint}")
  private String endpoint;
  @Value("${minio.accessKey}")
  private String accessKey;
  @Value("${minio.secretKey}")
  private String secretKey;

}
