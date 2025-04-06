package com.stayflow.infrastructure.adapter.aws;

import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.stayflow.application.port.out.ApplicationVariables;
import com.stayflow.application.port.out.ObjectStorage;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Repository
@RequiredArgsConstructor
public class ObjectStorageAdapter implements ObjectStorage {
  private final S3Client s3Client;
  private final ApplicationVariables props;

  @Override
  @SneakyThrows
  public String uploadImage(InputStream data, String content, String name, Long size) {
    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(props.getBucketName())
        .key(name)
        .contentType(content)
        .contentLength(size)
        .build();

    s3Client.putObject(request, RequestBody.fromInputStream(data, size));

    return s3Client.utilities()
        .getUrl(GetUrlRequest.builder()
            .bucket(props.getBucketName())
            .key(name)
            .build())
        .toString();
  }
}
