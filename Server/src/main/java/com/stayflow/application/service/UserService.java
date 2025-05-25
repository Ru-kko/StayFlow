package com.stayflow.application.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.stayflow.application.port.in.UserUseCase;
import com.stayflow.application.port.out.UserRepository;
import com.stayflow.domain.ErrorTypes;
import com.stayflow.domain.dto.UserRegister;
import com.stayflow.domain.role.Role;
import com.stayflow.domain.security.UserData;
import com.stayflow.domain.table.User;
import com.stayflow.infrastructure.error.StayFlowError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
  private final UserRepository userRepository;

  @Override
  public UserData findById(UUID id) throws StayFlowError {
    Optional<User> res = userRepository.findById(id);

    if (res.isEmpty()) {
      throw new StayFlowError(ErrorTypes.NOT_FOUND, "User not found");
    }

    return UserData.builder()
        .userId(res.get().getUserId())
        .email(res.get().getEmail())
        .firstName(res.get().getFirstName())
        .lastName(res.get().getLastName())
        .role(res.get().getRole())
        .build();
  }

  @Override
  public User findByEmail(String email) throws StayFlowError {
    Optional<User> res = userRepository.findByEmail(email);

    if (res.isEmpty()) {
      throw new StayFlowError(ErrorTypes.NOT_FOUND, "User not found");
    }

    return res.get();
  }

  @Override
  public UserData register(UserRegister userRegister) throws StayFlowError {
    validateUser(userRegister);

    User user = User.builder()
        .email(userRegister.getEmail())
        .firstName(userRegister.getFirstName())
        .lastName(userRegister.getLastName())
        .password(userRegister.getPassword())
        .role(Role.USER)
        .build();
    
    return UserData.fromUser(userRepository.save(user));
  }
  
  @Override
  public User findById(String id) throws StayFlowError {
    Optional<User> res = userRepository.findById(UUID.fromString(id));

    if (res.isEmpty()) {
      throw new StayFlowError(ErrorTypes.NOT_FOUND, "User not found");
    }

    return res.get();
  }

  private void validateUser(UserRegister user) throws StayFlowError {
    final String emailRegexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    if (user.getEmail() == null || !user.getEmail().matches(emailRegexp))
      throw new StayFlowError(ErrorTypes.BAD_REQUEST, "Email is invalid");

    if (user.getFirstName() == null || user.getFirstName().isEmpty())
      throw new StayFlowError(ErrorTypes.BAD_REQUEST, "First name is invalid");

    if (user.getLastName() == null || user.getLastName().isEmpty())
      throw new StayFlowError(ErrorTypes.BAD_REQUEST, "Last name is invalid");
  }

}