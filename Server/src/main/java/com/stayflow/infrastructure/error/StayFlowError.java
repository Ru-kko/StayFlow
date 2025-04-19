package com.stayflow.infrastructure.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StayFlowError extends Exception {
  private Integer code;
  private String message;
}
