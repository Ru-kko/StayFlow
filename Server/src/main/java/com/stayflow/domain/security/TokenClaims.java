package com.stayflow.domain.security;


import java.util.Map;
import java.util.UUID;

import com.stayflow.domain.role.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenClaims {
  private UUID userId;
  private Role role;

  public Map<String, Object> toMap() {
    return Map.of(
        "id", userId.toString(),
        "role", role.name()
    );
  }

  public static TokenClaims fromMap(Map<String, Object> claims) {
    return TokenClaims.builder()
        .userId(UUID.fromString((String) claims.get("id")))
        .role(Role.valueOf((String) claims.get("role")))
        .build();
  }
}
