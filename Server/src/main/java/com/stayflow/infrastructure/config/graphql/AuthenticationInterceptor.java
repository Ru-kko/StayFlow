package com.stayflow.infrastructure.config.graphql;

import java.util.List;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;

import com.stayflow.application.port.in.SessionUseCase;
import com.stayflow.domain.security.UserData;
import com.stayflow.infrastructure.error.StayFlowError;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements WebGraphQlInterceptor {
  private final SessionUseCase sessionService;

  @Override
  public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
    String token = extractToken(request);

    if (token == null)
      return chain.next(request);

    try {
      UserData userData = sessionService.getUserData(token);
      request.configureExecutionInput(
          (executionInput, builder) -> builder.graphQLContext(context -> context.put("currentUser", userData))
              .build());
    } catch (StayFlowError e) {
    }

    return chain.next(request);
  }

  private String extractToken(WebGraphQlRequest request) {
    List<String> authHeaders = request.getHeaders().get("Authorization");

    if (authHeaders == null || authHeaders.isEmpty())
      return null;

    String bearerToken = authHeaders.get(0);

    if (!bearerToken.startsWith("Bearer "))
      return null;

    return bearerToken.substring(7);
  }

}
