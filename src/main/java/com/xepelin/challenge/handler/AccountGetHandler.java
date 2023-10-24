package com.xepelin.challenge.handler;

import com.xepelin.challenge.core.HandlerResponseHelper;
import com.xepelin.challenge.model.dto.AccountDTO;
import com.xepelin.challenge.model.mapper.AccountMapper;
import com.xepelin.challenge.service.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Log4j2
public class AccountGetHandler {

  private static final String ROOT_ENDPOINT = "/account";
  private static final String PATH_VARIABLE_ID = "id";
  public static final String GET_ENDPOINT = ROOT_ENDPOINT + "/{" + PATH_VARIABLE_ID + "}";
  public static final String GET_ALL_ENDPOINT = ROOT_ENDPOINT;

  @NonNull private final AccountMapper accountMapper;
  @NonNull private final AccountService accountService;
  @NonNull private final HandlerResponseHelper handlerResponseHelper;

  public Mono<ServerResponse> get(ServerRequest serverRequest) {
    var id = serverRequest.pathVariable(PATH_VARIABLE_ID);
    var response = accountService
        .get(Long.valueOf(id))
        .map(accountMapper::toDTO);
    return handlerResponseHelper.buildOK(response, AccountDTO.class);
  }

  public Mono<ServerResponse> getAll() {
    var response = accountService
        .getAll()
        .map(accountMapper::toDTO);
    return handlerResponseHelper.buildOK(response, AccountDTO.class);
  }

}
