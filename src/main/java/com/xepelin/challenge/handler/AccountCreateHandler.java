package com.xepelin.challenge.handler;

import com.xepelin.challenge.core.AbstractValidationHandler;
import com.xepelin.challenge.core.HandlerResponseHelper;
import com.xepelin.challenge.model.dto.AccountDTO;
import com.xepelin.challenge.model.dto.CreateAccountDTO;
import com.xepelin.challenge.model.mapper.AccountMapper;
import com.xepelin.challenge.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class AccountCreateHandler extends AbstractValidationHandler<CreateAccountDTO, Validator> {

  private static final String ROOT_ENDPOINT = "/account";
  public static final String CREATE_ENDPOINT = ROOT_ENDPOINT;
  private final AccountMapper accountMapper;
  private final AccountService accountService;
  private final HandlerResponseHelper handlerResponseHelper;

  protected AccountCreateHandler(Validator validator, AccountMapper accountMapper,
      AccountService accountService, HandlerResponseHelper handlerResponseHelper) {
    super(CreateAccountDTO.class, validator);
    this.accountMapper = accountMapper;
    this.accountService = accountService;
    this.handlerResponseHelper = handlerResponseHelper;
  }

  @Override
  protected Mono<ServerResponse> processBody(CreateAccountDTO validBody, ServerRequest originalRequest) {
    var response = Mono.just(validBody)
        .doOnNext(createAccountDTO -> log.info("Receiving payload: {}", createAccountDTO))
        .map(accountMapper::toDAO)
        .flatMap(accountService::create)
        .map(accountMapper::toDTO);
    return handlerResponseHelper.buildOK(response, AccountDTO.class);
  }

}
