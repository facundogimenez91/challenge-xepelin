package com.xepelin.challenge.service;

import com.xepelin.challenge.model.dao.AccountDAO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

  Mono<AccountDAO> get(Long id);

  Flux<AccountDAO> getAll();

  Mono<AccountDAO> create(AccountDAO accountDAO);


  @Transactional(isolation = Isolation.REPEATABLE_READ)
  Mono<AccountDAO> update(AccountDAO accountDAO);

}
