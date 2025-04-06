package com.stayflow.util;

import org.springframework.data.domain.Page;

import com.stayflow.domain.dto.PageResponse;

public class PageConverter {
  public static <T> PageResponse<T> buildPageResponse(Page<T> page) {
    return PageResponse.<T>builder()
        .content(page.getContent())
        .totalItems(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .actualPage(page.getNumber() + 1)
        .build();
  }
}
