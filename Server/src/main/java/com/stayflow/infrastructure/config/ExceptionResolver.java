package com.stayflow.infrastructure.config;

import java.util.List;
import java.util.Map;

import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.stereotype.Component;

import com.stayflow.infrastructure.error.StayFlowError;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Component
public class ExceptionResolver implements DataFetcherExceptionResolver {
  @Override
  public Mono<List<GraphQLError>> resolveException(Throwable ex, DataFetchingEnvironment env) {
    if (!(ex instanceof StayFlowError)) {
      return Mono.empty();
    }

    StayFlowError e = (StayFlowError) ex;
    GraphQLError error = GraphqlErrorBuilder.newError(env)
      .message(e.getMessage())
      .extensions(Map.of("code", e.getCode()))
      .build();

    return Mono.just(List.of(error));
  }  
}
