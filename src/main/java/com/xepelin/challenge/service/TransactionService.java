package com.xepelin.challenge.service;

import com.xepelin.challenge.model.dao.TransactionDAO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

  Mono<TransactionDAO> create(TransactionDAO transactionDAO);

  Mono<TransactionDAO> get(Long id);

  Flux<TransactionDAO> getAll();

}
