package com.stayflow.infrastructure.config.graphql;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GraphQlMultipartHandler implements HandlerFunction<ServerResponse> {
  private static final ParameterizedTypeReference<Map<String, Object>> MAP_PARAMETERIZED_TYPE_REF = new ParameterizedTypeReference<Map<String, Object>>() {
  };
  private static final ParameterizedTypeReference<Map<String, List<String>>> LIST_PARAMETERIZED_TYPE_REF = new ParameterizedTypeReference<Map<String, List<String>>>() {
  };
  public static final List<MediaType> SUPPORTED_MEDIA_TYPES = Arrays.asList(MediaType.APPLICATION_JSON,
      MediaType.APPLICATION_GRAPHQL_RESPONSE);

  private final IdGenerator idGenerator = new AlternativeJdkIdGenerator();

  private final WebGraphQlHandler graphQlHandler;
  private final MappingJackson2HttpMessageConverter messageConverter;

  @Override
  public ServerResponse handle(ServerRequest req) throws ServletException {
    HttpServletRequest httpServletRequest = req.servletRequest();

    Map<String, Object> inputQuery = Optional.ofNullable(this.<Map<String, Object>>deserializePart(
        httpServletRequest,
        "operations",
        MAP_PARAMETERIZED_TYPE_REF.getType())).orElse(new HashMap<>());

    final Map<String, Object> queryVariables = getFromMapOrEmpty(inputQuery, "variables");
    final Map<String, Object> extensions = getFromMapOrEmpty(inputQuery, "extensions");

    Map<String, MultipartFile> fileParams = readMultipartFiles(httpServletRequest);

    Map<String, List<String>> fileMappings = Optional.ofNullable(this.<Map<String, List<String>>>deserializePart(
        httpServletRequest,
        "map",
        LIST_PARAMETERIZED_TYPE_REF.getType())).orElse(new HashMap<>());

    fileMappings.forEach((String fileKey, List<String> objectPaths) -> {
      MultipartFile file = fileParams.get(fileKey);
      if (file != null) {
        objectPaths.forEach((String objectPath) -> {
          MultipartVariableMapper.mapVariable(
              objectPath,
              queryVariables,
              file);
        });
      }
    });

    String query = (String) inputQuery.get("query");
    String opName = (String) inputQuery.get("operationName");

    Map<String, Object> body = new HashMap<>();
    body.put("query", query);
    body.put("operationName", StringUtils.hasText(opName) ? opName : "");
    body.put("variables", queryVariables);
    body.put("extensions", extensions);

    MultiValueMap<String, Cookie> source = req.cookies();
    MultiValueMap<String, HttpCookie> target = new LinkedMultiValueMap<>(source.size());
    source.values().forEach((cookieList) -> cookieList.forEach((cookie) -> {
      HttpCookie httpCookie = new HttpCookie(cookie.getName(), cookie.getValue());
      target.add(cookie.getName(), httpCookie);
    }));

    WebGraphQlRequest graphQlRequest = new WebGraphQlRequest(
        req.uri(), req.headers().asHttpHeaders(), target,
        req.remoteAddress().orElse(null), req.attributes(),
        body,
        this.idGenerator.generateId().toString(), LocaleContextHolder.getLocale());

    var future = this.graphQlHandler.handleRequest(graphQlRequest)
        .map(response -> {
          ServerResponse.BodyBuilder builder = ServerResponse.ok();
          builder.headers(headers -> headers.putAll(response.getResponseHeaders()));
          builder.contentType(selectResponseMediaType(req));
          return builder.body(response.toMap());
        }).toFuture();

    if (future.isDone()) {
      try {
        return future.get();
      } catch (ExecutionException ex) {
        throw new ServletException(ex.getCause());
      } catch (InterruptedException ex) {
        throw new ServletException(ex);
      }
    }
    
    return ServerResponse.async(future);
  }

  private static class JsonMultipartInputMessage implements HttpInputMessage {
    private final Part part;

    JsonMultipartInputMessage(Part part) {
      this.part = part;
    }

    @Override
    public InputStream getBody() throws IOException {
      return this.part.getInputStream();
    }

    @Override
    public HttpHeaders getHeaders() {
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      return httpHeaders;
    }
  }

  @SuppressWarnings("unchecked")
  private <T> T deserializePart(HttpServletRequest httpServletRequest, String name, Type type) {
    try {
      Part part = httpServletRequest.getPart(name);
      if (part == null) {
        return null;
      }
      return (T) this.messageConverter.read(type, null, new JsonMultipartInputMessage(part));
    } catch (IOException | ServletException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> getFromMapOrEmpty(Map<String, Object> input, String key) {
    if (input.containsKey(key)) {
      return (Map<String, Object>) input.get(key);
    } else {
      return new HashMap<>();
    }
  }

  private static Map<String, MultipartFile> readMultipartFiles(HttpServletRequest httpServletRequest) {
    MultipartHttpServletRequest multipartHttpServletRequest = WebUtils.getNativeRequest(httpServletRequest,
        MultipartHttpServletRequest.class);
    return multipartHttpServletRequest.getFileMap();
  }

  private static MediaType selectResponseMediaType(ServerRequest serverRequest) {
    for (MediaType accepted : serverRequest.headers().accept()) {
      if (SUPPORTED_MEDIA_TYPES.contains(accepted)) {
        return accepted;
      }
    }
    return MediaType.APPLICATION_JSON;
  }
}
