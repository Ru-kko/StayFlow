package com.stayflow.infrastructure.controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.stayflow.application.port.in.SessionUseCase;
import com.stayflow.application.port.in.UserUseCase;
import com.stayflow.domain.dto.UserRegister;
import com.stayflow.domain.security.TokenClaims;
import com.stayflow.domain.security.TokenResponse;
import com.stayflow.domain.security.UserData;
import com.stayflow.domain.security.UserLogin;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Controller
@RequiredArgsConstructor
public class AuthController {
  public final SessionUseCase sessionService;
  public final UserUseCase userService;

  @MutationMapping
  @SneakyThrows
  TokenResponse loginUser(@Argument("creds") UserLogin creds) {
    return sessionService.login(creds);
  }

  @MutationMapping
  @SneakyThrows
  TokenResponse registerUser(@Argument("user") UserRegister user) {
    return sessionService.register(user);
  }

  
  @SneakyThrows
  @QueryMapping
  @PreAuthorize("isAuthenticated()")
  UserData me(Authentication auth) {
    TokenClaims claims = (TokenClaims) auth.getPrincipal();
    return userService.findById(claims.getUserId());
  }
}
