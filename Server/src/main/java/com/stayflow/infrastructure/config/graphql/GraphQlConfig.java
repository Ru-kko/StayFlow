package com.stayflow.infrastructure.config.graphql;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;

@Configuration
public class GraphQlConfig {
  @Bean
  RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder -> wiringBuilder
        .scalar(ExtendedScalars.GraphQLLong)
        .scalar(ExtendedScalars.UUID)
        .scalar(GraphQLScalarType.newScalar().name("Upload").coercing(new UploadCoercing()).build());
  }

  @Bean
  @Order(-10)
  RouterFunction<ServerResponse> graphqlMultipartRouterFunction(
      GraphQlProperties props,
      WebGraphQlHandler webGraphQlHandler,
      ObjectMapper objectMapper) {
    String gqlPath = props.getPath();
    RouterFunctions.Builder builder = RouterFunctions.route();
    GraphQlMultipartHandler handler = new GraphQlMultipartHandler(webGraphQlHandler,
        new MappingJackson2HttpMessageConverter(objectMapper));

    builder = builder.POST(gqlPath,
        RequestPredicates.contentType(MediaType.MULTIPART_FORM_DATA)
            .and(RequestPredicates.accept(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG, MediaType.APPLICATION_JSON)),
        handler);

    return builder.build();
  }
}
