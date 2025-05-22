package com.stayflow.domain.security;

import java.util.UUID;

import com.stayflow.domain.role.Role;
import com.stayflow.domain.table.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
  private UUID userId;
  private String email;
  private String firstName;
  private String lastName;
  private Role role;

  public static UserData fromUser(User user) {
    return UserData.builder()
        .userId(user.getUserId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .role(user.getRole())
        .build();
  }
}
