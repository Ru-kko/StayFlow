package com.stayflow.infrastructure.adapter.aws;

import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.stayflow.application.port.out.ApplicationVariables;
import com.stayflow.application.port.out.ObjectStorage;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ObjectStorageAdapter implements ObjectStorage {
  private final MinioClient minio;
  private final ApplicationVariables props;

  @Override
  @SneakyThrows
  public String uploadImage(InputStream data, String content, String name, Long size) {
    ObjectWriteResponse res = minio.putObject(
        PutObjectArgs.builder()
            .bucket(props.getBucketName())
            .object(name)
            .stream(data, -1, 5242880 ) // 5MB
            .contentType(content)
            .build());

    return res.object();
  }
}
