package com.stayflow.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ObjectStorageConfig {
  private final ApplicationConfiguration props;

  @Bean
  MinioClient minioClient() {
    return MinioClient.builder()
        .endpoint(props.getEndpoint())
        .credentials(props.getAccessKey(), props.getSecretKey())
        .build();
  }
}
