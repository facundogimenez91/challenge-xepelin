package com.xepelin.challenge.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PayloadValidationException extends HttpStatusException {

  public PayloadValidationException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }

}
