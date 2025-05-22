package com.stayflow.infrastructure.config.security;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stayflow.application.port.in.JWTUseCase;
import com.stayflow.domain.security.TokenClaims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final UserDetailsService userDetailsService;
  private final JWTUseCase jwtService;

  @Override
  @SneakyThrows
  protected void doFilterInternal(HttpServletRequest req,
      HttpServletResponse res,
      FilterChain filterChain) {

    String bearerTk = req.getHeader("Authorization");

    if (bearerTk == null || !bearerTk.startsWith("Bearer ")) {
      filterChain.doFilter(req, res);
      return;
    }
    try {
      bearerTk = bearerTk.replace("Bearer ", "");

      TokenClaims data = jwtService.verifyToken(bearerTk);

      UserDetails userDetails = userDetailsService.loadUserByUsername(data.getUserId().toString());


      UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(data, null,
          userDetails.getAuthorities());

      userPassAuthToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(req));

      SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
    } catch (UsernameNotFoundException e) {
      return;
    }

    filterChain.doFilter(req, res);
  }
}
