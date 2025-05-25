package com.stayflow.application.service;

import org.springframework.stereotype.Service;

import com.stayflow.application.port.in.JWTUseCase;
import com.stayflow.application.port.in.SessionUseCase;
import com.stayflow.application.port.in.UserUseCase;
import com.stayflow.application.port.out.OutPasswordEncoder;
import com.stayflow.domain.ErrorTypes;
import com.stayflow.domain.dto.UserRegister;
import com.stayflow.domain.security.TokenClaims;
import com.stayflow.domain.security.TokenResponse;
import com.stayflow.domain.security.UserData;
import com.stayflow.domain.security.UserLogin;
import com.stayflow.domain.table.User;
import com.stayflow.infrastructure.error.StayFlowError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService implements SessionUseCase {
  private final UserUseCase userService;
  private final JWTUseCase tokenService;
  private final OutPasswordEncoder encoder;

  @Override
  public TokenResponse register(UserRegister user) throws StayFlowError {
    String chipherPassword = encoder.encode(user.getPassword());
    UserRegister clone = UserRegister.builder()
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .password(chipherPassword)
        .build();

    UserData saved = userService.register(clone);

    return tokenService.buildToken(saved);
  }

  @Override
  public TokenResponse login(UserLogin login) throws StayFlowError {
    User user = userService.findByEmail(login.getEmail());

    if (!encoder.matches(login.getPassword(), user.getPassword()))
      throw new StayFlowError(ErrorTypes.INVALID_CREDENTIALS, "Password is invalid");

    return tokenService.buildToken(UserData.fromUser(user));
  }

  @Override
  public UserData getUserData(String token) throws StayFlowError {
    TokenClaims claims = tokenService.verifyToken(token);
    return userService.findById(claims.getUserId());
  }
  
}
