package com.xepelin.challenge.config;

import com.xepelin.challenge.handler.AccountCreateHandler;
import com.xepelin.challenge.handler.AccountGetHandler;
import com.xepelin.challenge.handler.TransactionCreateHandler;
import com.xepelin.challenge.handler.TransactionGetHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class RouteConfig {

  @Bean
  public RouterFunction<ServerResponse> execute(AccountGetHandler accountGetHandler, AccountCreateHandler accountCreateHandler, TransactionGetHandler transactionGetHandler, TransactionCreateHandler transactionCreateHandler) {
    return RouterFunctions.route()
                          .GET(AccountGetHandler.GET_ENDPOINT, accountGetHandler::get)
                          .GET(AccountGetHandler.GET_ALL_ENDPOINT, serverRequest -> accountGetHandler.getAll())
                          .POST(AccountCreateHandler.CREATE_ENDPOINT, accountCreateHandler::handleRequest)
                          .GET(TransactionGetHandler.GET_ENDPOINT, transactionGetHandler::get)
                          .GET(TransactionGetHandler.GET_ALL_ENDPOINT, serverRequest -> transactionGetHandler.getAll())
                          .POST(TransactionCreateHandler.CREATE_ENDPOINT, transactionCreateHandler::handleRequest)
                          .build();
  }

}
