package com.xepelin.challenge.handler;

import com.xepelin.challenge.core.AbstractValidationHandler;
import com.xepelin.challenge.core.HandlerResponseHelper;
import com.xepelin.challenge.model.dto.CreateTransactionDTO;
import com.xepelin.challenge.model.dto.TransactionDTO;
import com.xepelin.challenge.model.mapper.TransactionMapper;
import com.xepelin.challenge.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class TransactionCreateHandler extends AbstractValidationHandler<CreateTransactionDTO, Validator> {

  private static final String ROOT_ENDPOINT = "/transaction";

  public static final String CREATE_ENDPOINT = ROOT_ENDPOINT;

  private final TransactionService transactionService;
  private final TransactionMapper transactionMapper;
  private final HandlerResponseHelper handlerResponseHelper;

  protected TransactionCreateHandler(Validator validator, TransactionMapper transactionMapper,
      TransactionService transactionService, HandlerResponseHelper handlerResponseHelper) {
    super(CreateTransactionDTO.class, validator);
    this.transactionMapper = transactionMapper;
    this.transactionService = transactionService;
    this.handlerResponseHelper = handlerResponseHelper;
  }

  @Override
  protected Mono<ServerResponse> processBody(CreateTransactionDTO validBody, ServerRequest originalRequest) {
    var response = Mono.just(validBody)
        .doOnNext(createAccountDTO -> log.info("Receiving payload: {}", createAccountDTO))
        .map(transactionMapper::toDAO)
        .flatMap(transactionService::create)
        .map(transactionMapper::toDTO);
    return handlerResponseHelper.buildOK(response, TransactionDTO.class);
  }

}
