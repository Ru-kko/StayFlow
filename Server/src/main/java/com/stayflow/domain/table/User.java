package com.stayflow.domain.table;

import java.util.UUID;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.UuidGenerator;

import com.stayflow.domain.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "\"User\"")
public class User {
  @Id
  @UuidGenerator
  @GeneratedValue  
  private UUID userId;

  @Unique
  private String email;
  private String firstName;
  private String lastName;

  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;
}
