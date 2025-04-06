package com.stayflow.application.port.out;

import com.stayflow.domain.table.Image;

public interface ImageRepository {
  Image save(Image img);
}
