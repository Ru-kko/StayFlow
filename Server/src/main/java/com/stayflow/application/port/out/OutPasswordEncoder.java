package com.stayflow.application.port.out;

public interface OutPasswordEncoder {
  String encode(String plainPassword);
  Boolean matches(String password, String chipher);
}