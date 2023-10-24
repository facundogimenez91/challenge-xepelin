package com.xepelin.challenge.service;

import com.xepelin.challenge.model.EventTypeEnum;
import com.xepelin.challenge.model.TransactionTypeEnum;
import com.xepelin.challenge.model.dao.AccountDAO;
import com.xepelin.challenge.model.dao.EventDAO;
import com.xepelin.challenge.model.dao.TransactionDAO;
import com.xepelin.challenge.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

  @Mock
  private AccountService accountService;
  @Mock
  private EventService eventService;
  @Mock
  private TransactionRepository transactionRepository;
  @InjectMocks
  private TransactionServiceImpl service;
  private TransactionDAO createValue;
  private Mono<TransactionDAO> createResult;
  private TransactionDAO expectedCreateValue;

  @Test
  void givenRequest_whenExecuteCreate_thenIsOk() {
    givenCreateParameters();
    givenExpectedCreateResponse();
    givenService();
    whenExecuteCreate();
    thenIsOkCreate();
  }

  private void givenCreateParameters() {
    createValue = TransactionDAO
        .builder()
        .accountId(1L)
        .amount(BigDecimal.valueOf(10000))
        .type(TransactionTypeEnum.DEPOSIT)
        .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
        .build();
  }

  private void givenService() {
    when(eventService.create(any(EventDAO.class))).thenReturn(
        Mono.just(EventDAO.builder()
                          .type(EventTypeEnum.TRX_DEPOSIT)
                          .accountId(1L)
                          .amount(BigDecimal.valueOf(1000))
                          .createdAt(OffsetDateTime.now(Clock.systemUTC()))
                          .build())
    );
    when(accountService.get(any(Long.class))).thenReturn(
        Mono.just(AccountDAO
            .builder()
            .id(1L)
            .name("my-account")
            .balance(BigDecimal.valueOf(10000))
            .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
            .build())
    );

    when(accountService.update(any(AccountDAO.class))).thenReturn(
        Mono.just(AccountDAO
            .builder()
            .id(1L)
            .name("my-account")
            .balance(BigDecimal.valueOf(10000))
            .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
            .build())
    );

    when(transactionRepository.save(any(TransactionDAO.class))).thenReturn(
        Mono.just(createValue)
    );
    when(transactionRepository.findById(any(Long.class))).thenReturn(
        Mono.just(createValue)
    );
    when(transactionRepository.findAll()).thenReturn(
        Flux.just(createValue)
    );
  }

  private void givenExpectedCreateResponse() {
    expectedCreateValue = TransactionDAO
        .builder()
        .accountId(1L)
        .amount(BigDecimal.valueOf(10000))
        .type(TransactionTypeEnum.DEPOSIT)
        .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
        .build();
  }

  private void whenExecuteCreate() {
    createResult = service.create(createValue);
  }

  private void thenIsOkCreate() {
    StepVerifier
        .create(createResult).expectNextMatches(e -> e.equals(expectedCreateValue)).verifyComplete();
  }


}
