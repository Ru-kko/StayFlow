package com.stayflow.infrastructure.adapter.persistence;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.stayflow.application.port.out.UserRepository;
import com.stayflow.domain.table.User;
import com.stayflow.infrastructure.adapter.persistence.repository.JPAUserRepository;
import com.stayflow.infrastructure.error.StayFlowError;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JPAUSerRepositoryAdapter implements UserRepository {
  private final JPAUserRepository userRepository;

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User save(User user) throws StayFlowError {
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      isDuplicateNameViolation(e);
    }
    return null;
  }

  @Override
  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  }

  private void isDuplicateNameViolation(DataIntegrityViolationException e) throws StayFlowError {
    Throwable rootCause = e.getCause();
    if (!(rootCause instanceof ConstraintViolationException)) {
      throw e;
    }
    String sqlState = ((org.hibernate.exception.ConstraintViolationException) rootCause).getSQLState();
    if ("23505".equals(sqlState)) {
      throw new StayFlowError(400, "Already exists an user with this email");
    }

    throw e;
  }
}
