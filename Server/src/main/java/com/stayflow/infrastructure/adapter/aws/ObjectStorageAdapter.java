package com.stayflow.infrastructure.adapter.aws;

import java.io.InputStream;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.stayflow.application.port.out.ApplicationVariables;
import com.stayflow.application.port.out.ObjectStorage;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Repository
@RequiredArgsConstructor
public class ObjectStorageAdapter implements ObjectStorage {
  private final AmazonS3 amazonS3;
  private final ApplicationVariables props;

  @Override
  @SneakyThrows
  public String uploadImage(InputStream data, String content, String name, Long size) {
    ObjectMetadata metadata = new ObjectMetadata();

    metadata.setContentType(content);
    metadata.setContentLength(size);

    amazonS3.putObject(new PutObjectRequest(props.getBucketName(), name, data, metadata));

    return amazonS3.getUrl(props.getBucketName(), name).toString();
  }
}
