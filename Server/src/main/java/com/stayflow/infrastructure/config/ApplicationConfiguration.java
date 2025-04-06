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
  @Value("${app.bucket-name}")
  private String bucketName;

  @Value("${minio.endpoint}")
  private String endpoint;
  @Value("${minio.access-key}")
  private String accessKey;
  @Value("${minio.secret-key}")
  private String secretKey;

}
