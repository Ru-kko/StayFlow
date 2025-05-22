package com.stayflow.application.port.in;

import com.stayflow.domain.dto.UserRegister;
import com.stayflow.domain.security.TokenResponse;
import com.stayflow.domain.security.UserData;
import com.stayflow.domain.security.UserLogin;
import com.stayflow.infrastructure.error.StayFlowError;

public interface SessionUseCase {
  TokenResponse register(UserRegister user) throws StayFlowError;
  TokenResponse login(UserLogin login) throws StayFlowError;
  UserData getUserData(String token) throws StayFlowError;
}
