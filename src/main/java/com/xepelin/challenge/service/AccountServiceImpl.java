package com.xepelin.challenge.service;

import com.xepelin.challenge.model.EventTypeEnum;
import com.xepelin.challenge.model.dao.AccountDAO;
import com.xepelin.challenge.model.dao.EventDAO;
import com.xepelin.challenge.model.exception.AccountServiceException;
import com.xepelin.challenge.repository.AccountRepository;
import java.time.Clock;
import java.time.OffsetDateTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  @NonNull
  private AccountRepository accountRepository;
  @NonNull
  private EventService eventService;

  @Override
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public Mono<AccountDAO> get(Long id) {
    return accountRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new AccountServiceException("Account not found", HttpStatus.NOT_FOUND)));
  }

  @Override
  public Flux<AccountDAO> getAll() {
    return accountRepository.findAll();
  }

  @Override
  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public Mono<AccountDAO> create(AccountDAO accountDAO) {
    return accountRepository.save(accountDAO)
                            .publishOn(Schedulers.boundedElastic())
                            .doOnNext(accountDAO1 -> eventService.create(EventDAO.builder()
                                                           .type(EventTypeEnum.ACCT_CREATION)
                                                           .accountId(accountDAO1.getId())
                                                           .amount(accountDAO1.getBalance())
                                                           .createdAt(OffsetDateTime.now(Clock.systemUTC()))
                                                           .build()).subscribe());
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Mono<AccountDAO> update(AccountDAO accountDAO) {
    return accountRepository.save(accountDAO);
  }

}
