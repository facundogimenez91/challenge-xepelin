package com.xepelin.challenge.core;

import com.xepelin.challenge.model.dto.ErrorResponseDTO;
import com.xepelin.challenge.model.exception.HttpStatusException;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@Log4j2
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

  private final HandlerResponseHelper handlerResponseHelper;

  public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer, HandlerResponseHelper handlerResponseHelper) {
    super(errorAttributes, webProperties.getResources(), applicationContext);
    this.setMessageWriters(serverCodecConfigurer.getWriters());
    this.handlerResponseHelper = handlerResponseHelper;
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
    var error = getError(serverRequest);
    if (error instanceof HttpStatusException httpStatusException) {
      return httpStatusExceptionHandler(httpStatusException);
    }
    return genericExceptionHandler(error);
  }

  protected Mono<ServerResponse> genericExceptionHandler(Throwable throwable) {
    var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    var routeCause = findRootCause(throwable);
    var errorResponse = ErrorResponseDTO
        .builder()
        .httpStatusCode(httpStatus.value())
        .errorDetail(routeCause.getMessage())
        .build();
    log.error("Sending error: {}", errorResponse);
    return handlerResponseHelper.build(httpStatus, Mono.just(errorResponse), ErrorResponseDTO.class);
  }

  protected Mono<ServerResponse> httpStatusExceptionHandler(HttpStatusException httpStatusException) {
    var httpStatus = httpStatusException.getHttpStatus();
    var errorResponse = ErrorResponseDTO
        .builder()
        .httpStatusCode(httpStatus.value())
        .errorDetail(httpStatusException.getMessage())
        .build();
    log.error("Sending error: {}", errorResponse);
    return handlerResponseHelper.build(httpStatus, Mono.just(errorResponse), ErrorResponseDTO.class);
  }

  private Throwable findRootCause(Throwable throwable) {
    Objects.requireNonNull(throwable);
    var rootCause = throwable;
    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
      rootCause = rootCause.getCause();
    }
    return rootCause;
  }

}
