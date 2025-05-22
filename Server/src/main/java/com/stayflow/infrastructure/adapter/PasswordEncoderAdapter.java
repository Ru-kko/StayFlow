package com.stayflow.infrastructure.adapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.stayflow.application.port.out.OutPasswordEncoder;

@Component
public class PasswordEncoderAdapter implements OutPasswordEncoder {
  private final PasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public Boolean matches(String password, String chipher) {
    return encoder.matches(password, chipher);
  }

  @Override
  public String encode(String plainPassword) {
    return encoder.encode(plainPassword);
  }

}
