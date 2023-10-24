package com.xepelin.challenge.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountServiceException extends HttpStatusException {

  public AccountServiceException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }

}
