package com.xepelin.challenge.handler;

import com.xepelin.challenge.core.HandlerResponseHelper;
import com.xepelin.challenge.model.dto.TransactionDTO;
import com.xepelin.challenge.model.mapper.TransactionMapper;
import com.xepelin.challenge.service.TransactionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class TransactionGetHandler {

  private static final String ROOT_ENDPOINT = "/transaction";

  private static final String PATH_VARIABLE_ID = "id";

  public static final String GET_ENDPOINT = ROOT_ENDPOINT + "/{" + PATH_VARIABLE_ID + "}";

  public static final String GET_ALL_ENDPOINT = ROOT_ENDPOINT;

  @NonNull
  private TransactionService transactionService;

  @NonNull
  private TransactionMapper transactionMapper;

  @NonNull
  private HandlerResponseHelper handlerResponseHelper;

  public Mono<ServerResponse> get(ServerRequest serverRequest) {
    var id = serverRequest.pathVariable(PATH_VARIABLE_ID);
    var response = transactionService
        .get(Long.valueOf(id))
        .map(transactionMapper::toDTO);
    return handlerResponseHelper.buildOK(response, TransactionDTO.class);
  }

  public Mono<ServerResponse> getAll() {
    var response = transactionService
        .getAll()
        .map(transactionMapper::toDTO);
    return handlerResponseHelper.buildOK(response, TransactionDTO.class);
  }

}
