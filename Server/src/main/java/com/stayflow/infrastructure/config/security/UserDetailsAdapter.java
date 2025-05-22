package com.stayflow.infrastructure.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.stayflow.application.port.in.UserUseCase;
import com.stayflow.domain.table.User;
import com.stayflow.infrastructure.error.StayFlowError;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class UserDetailsAdapter implements UserDetailsService {
  private UserUseCase userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userService.findById(username);

      return new UserDetailsImpl(user);
    } catch (StayFlowError e) {
      throw new UsernameNotFoundException("User not found");
    }
  }

  @AllArgsConstructor
  public static class UserDetailsImpl implements UserDetails {
    private final User usr;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      GrantedAuthority auth = () -> "ROLE_".concat(usr.getRole().toString());

      return List.of(auth);
    }

    @Override
    public String getPassword() {
      return usr.getPassword();
    }

    @Override
    public String getUsername() {
      return usr.getUserId().toString();
    }
  }
}
