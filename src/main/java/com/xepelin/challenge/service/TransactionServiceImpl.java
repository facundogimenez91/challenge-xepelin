package com.xepelin.challenge.service;

import com.xepelin.challenge.model.EventTypeEnum;
import com.xepelin.challenge.model.TransactionTypeEnum;
import com.xepelin.challenge.model.dao.AccountDAO;
import com.xepelin.challenge.model.dao.EventDAO;
import com.xepelin.challenge.model.dao.TransactionDAO;
import com.xepelin.challenge.model.exception.TransactionServiceException;
import com.xepelin.challenge.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Log4j2
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  @NonNull
  private AccountService accountService;
  @NonNull
  private EventService eventService;
  @NonNull
  private TransactionRepository transactionRepository;

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Mono<TransactionDAO> create(TransactionDAO transactionDAO) {
    return accountService
        .get(transactionDAO.getAccountId())
        .flatMap(accountDAO -> applyOperation(accountDAO, transactionDAO))
        .flatMap(accountService::update)
        .then(transactionRepository.save(transactionDAO))
        .publishOn(Schedulers.boundedElastic())
        .doOnNext(transactionDAO1 -> eventService.create(EventDAO
            .builder()
            .type(EventTypeEnum.getByTrxType(transactionDAO1.getType()))
            .amount(transactionDAO1.getAmount())
            .accountId(transactionDAO.getAccountId())
            .createdAt(OffsetDateTime.now(Clock.systemUTC()))
            .build()).subscribe());
  }

  @Override
  public Mono<TransactionDAO> get(Long id) {
    return transactionRepository.findById(id);
  }

  @Override
  public Flux<TransactionDAO> getAll() {
    return transactionRepository.findAll();
  }

  private Mono<AccountDAO> applyOperation(AccountDAO accountDAO, TransactionDAO transactionDAO) {
    var balance = accountDAO.getBalance();
    var transactionType = transactionDAO.getType();
    var amount = transactionDAO.getAmount();
    if (transactionType.equals(TransactionTypeEnum.DEPOSIT) && amount.compareTo(BigDecimal.valueOf(10000)) > 0)
      log.warn("A deposit greater than $10000.- has been detected: Amount: ${}.-", amount);
    switch (transactionType) {
      case DEPOSIT:
        accountDAO.setBalance(balance.add(amount));
        break;
      case WITHDRAW:
        if (balance.compareTo(amount) < 0)
          return Mono.error(new TransactionServiceException("Insufficient balance", HttpStatus.BAD_REQUEST));
        accountDAO.setBalance(balance.subtract(amount));
        break;
      default:
        return Mono.error(new TransactionServiceException("Unsupported transaction type", HttpStatus.BAD_REQUEST));
    }
    return Mono.just(accountDAO);
  }

}
