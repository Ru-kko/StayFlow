package com.stayflow.application.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.stayflow.application.port.in.JWTUseCase;
import com.stayflow.domain.security.TokenClaims;
import com.stayflow.domain.security.TokenResponse;
import com.stayflow.domain.security.UserData;
import com.stayflow.infrastructure.config.ApplicationConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTService implements JWTUseCase {
  private final ApplicationConfiguration props;

  @Override
  public TokenResponse buildToken(UserData userClaims) {
    Map<String, Object> claims = Map.of(
        "id", userClaims.getUserId().toString(),
        "role", userClaims.getRole().name()
    );


    Long now = System.currentTimeMillis();
    Long expiration = now + props.getJwtExpiration();
    String token = Jwts.builder()
        .subject(userClaims.getEmail())
        .issuedAt(new Date(now))
        .expiration(new Date(expiration))
        .signWith(getSecretKey())
        .claims(claims)
        .compact();

        return TokenResponse.builder()
            .token(token)
            .expiresIn(props.getJwtExpiration())
            .issuedAt(now)
            .build();
  }
 

  @Override
  public TokenClaims verifyToken(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    TokenClaims tokenClaims = TokenClaims.fromMap(claims);

    return tokenClaims;
  }

  private SecretKey getSecretKey() {
    byte[] key = props.getJwtSecret().getBytes();
    return Keys.hmacShaKeyFor(key);
  }
}