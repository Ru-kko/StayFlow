package com.stayflow.application.port.in;

import java.util.UUID;

import com.stayflow.domain.dto.UserRegister;
import com.stayflow.domain.security.UserData;
import com.stayflow.domain.table.User;
import com.stayflow.infrastructure.error.StayFlowError;

public interface UserUseCase {
  UserData findById(UUID id) throws StayFlowError;
  User findById(String id) throws StayFlowError;
  User findByEmail(String email) throws StayFlowError;
  UserData register(UserRegister userRegister) throws StayFlowError;
}