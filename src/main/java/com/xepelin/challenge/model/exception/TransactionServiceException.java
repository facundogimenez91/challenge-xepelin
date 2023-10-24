package com.xepelin.challenge.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TransactionServiceException extends HttpStatusException {

  public TransactionServiceException(String message, HttpStatus httpStatus) {
    super(message, httpStatus);
  }

}
