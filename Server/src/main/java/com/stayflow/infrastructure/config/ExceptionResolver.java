package com.stayflow.infrastructure.config;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import com.stayflow.infrastructure.error.StayFlowError;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter  {
  @Override
  public GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    if (!(ex.getCause() instanceof StayFlowError)) {
      return null;
    }

    StayFlowError e = (StayFlowError) ex.getCause();
    GraphQLError error = GraphqlErrorBuilder.newError(env)
      .message(e.getMessage())
      .errorType(e.getCode())
      .path(env.getExecutionStepInfo().getPath())
      .build();

    return error;
  }  
}
