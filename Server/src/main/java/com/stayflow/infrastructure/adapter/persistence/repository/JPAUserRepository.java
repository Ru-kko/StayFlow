package com.stayflow.infrastructure.adapter.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayflow.domain.table.User;

public interface JPAUserRepository extends JpaRepository<User, UUID> { 
  Optional<User> findByEmail(String email);
}
