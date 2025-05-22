package com.stayflow.application.port.in;

import com.stayflow.domain.security.TokenClaims;
import com.stayflow.domain.security.TokenResponse;
import com.stayflow.domain.security.UserData;

public interface JWTUseCase {
  TokenResponse buildToken(UserData userClaims);
  TokenClaims verifyToken(String token);
}
