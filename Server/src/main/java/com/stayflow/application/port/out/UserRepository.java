package com.stayflow.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.stayflow.domain.table.User;
import com.stayflow.infrastructure.error.StayFlowError;

public interface UserRepository {
  Optional<User> findByEmail(String email);
  User save(User user) throws StayFlowError;
  Optional<User> findById(UUID id);
}