package com.xepelin.challenge.core;

import java.net.URI;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class HandlerResponseHelper {

  private static final String FORMAT_RESPONSE = "Generating Response with httpStatus={}";

  private static final String FORMAT_SUCCESS_RESPONSE = "Generating Response with httpStatus={} and successResponse";

  public Mono<ServerResponse> build(final HttpStatus httpStatus, Mono<? extends HandlerResponse> handlerResponse,
      Class<?> targetClass) {
    return ServerResponse
        .status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .body(handlerResponse, targetClass)
        .doOnNext(e -> log.debug(FORMAT_SUCCESS_RESPONSE, httpStatus));
  }

  public Mono<ServerResponse> build(final HttpStatus httpStatus) {
    return ServerResponse
        .status(httpStatus)
        .build()
        .doOnNext(e -> log.debug(FORMAT_RESPONSE, httpStatus));
  }

  public Mono<ServerResponse> build(final HttpStatus httpStatus, Flux<? extends HandlerResponse> handlerResponse,
      Class<?> targetClass) {
    return ServerResponse
        .status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .body(handlerResponse, targetClass)
        .doOnNext(e -> log.debug(FORMAT_SUCCESS_RESPONSE, httpStatus));
  }

  public Mono<ServerResponse> buildOK(Mono<? extends HandlerResponse> handlerResponse, Class<?> targetClass) {
    return build(HttpStatus.OK, handlerResponse, targetClass);
  }

  public Mono<ServerResponse> buildOK(Flux<? extends HandlerResponse> handlerResponse, Class<?> targetClass) {
    return build(HttpStatus.OK, handlerResponse, targetClass);
  }

  public Mono<ServerResponse> buildOK() {
    return build(HttpStatus.OK);
  }

  public Mono<ServerResponse> buildRedirect(URI uri) {
    return ServerResponse
        .status(HttpStatus.FOUND)
        .location(uri)
        .build();
  }

}
