package com.stayflow.infrastructure.error;

import com.stayflow.domain.ErrorTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StayFlowError extends Exception {
  private ErrorTypes code;
  private String message;
}
