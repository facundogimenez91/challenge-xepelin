package com.xepelin.challenge.core;

import com.xepelin.challenge.model.exception.PayloadValidationException;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class AbstractValidationHandler<T, U extends Validator> {

  private final Class<T> validationClass;

  private final U validator;

  protected AbstractValidationHandler(Class<T> clazz, U validator) {
    this.validationClass = clazz;
    this.validator = validator;
  }

  protected abstract Mono<ServerResponse> processBody(T validBody, final ServerRequest originalRequest);

  public final Mono<ServerResponse> handleRequest(final ServerRequest request) {
    return request.bodyToMono(this.validationClass)
                  .flatMap(body -> {
                    var errors = new BeanPropertyBindingResult(body, this.validationClass.getName());
                    validator.validate(body, errors);
                    if (errors.getAllErrors().isEmpty()) {
                      return processBody(body, request);
                    } else {
                      return onValidationErrors(errors);
                    }
                  });
  }

  protected Mono<ServerResponse> onValidationErrors(Errors errors) {
    return Mono.error(new PayloadValidationException(Objects
        .requireNonNull(errors.getFieldError())
        .getDefaultMessage(), HttpStatus.BAD_REQUEST));
  }

}