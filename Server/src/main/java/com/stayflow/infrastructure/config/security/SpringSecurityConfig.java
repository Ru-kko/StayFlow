package com.stayflow.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.stayflow.application.port.in.JWTUseCase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
  private final UserDetailsService userDetailsService;
  private final JWTUseCase jwtService;

  @Bean
  @SneakyThrows
  SecurityFilterChain filterChain(HttpSecurity http) {
    return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/graphql").permitAll()
            .requestMatchers("/graphiql/**").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(new JwtAuthFilter(userDetailsService, jwtService), UsernamePasswordAuthenticationFilter.class)
        .userDetailsService(userDetailsService)
        .build();
  }
}
