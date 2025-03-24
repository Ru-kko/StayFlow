package com.stayflow.application.port.out;

import java.io.InputStream;

public interface ObjectStorage {
  String uploadImage(InputStream data, String content, String name, Long Size);
}
