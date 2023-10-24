package com.xepelin.challenge.service;

import com.xepelin.challenge.model.EventTypeEnum;
import com.xepelin.challenge.model.dao.AccountDAO;
import com.xepelin.challenge.model.dao.EventDAO;
import com.xepelin.challenge.repository.AccountRepository;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

  @Mock
  private EventService eventService;
  @Mock
  private AccountRepository accountRepository;
  private AccountDAO createValue;
  private AccountDAO expectedCreateValue;
  private Mono<AccountDAO> createResult;
  private Long getValue;
  private AccountDAO expectedGetValue;
  private Mono<AccountDAO> getResult;
  @InjectMocks
  private AccountServiceImpl service;

  @Test
  void givenRequest_whenExecuteCreate_thenIsOk() {
    givenCreateParameters();
    givenExpectedCreateResponse();
    givenServiceForCreate();
    whenExecuteCreate();
    thenIsOkCreate();
  }

  @Test
  void givenRequest_whenExecuteGet_thenIsOk() {
    givenGetParameters();
    givenExpectedGetResponse();
    givenServiceForGet();
    whenExecuteGet();
    thenIsOkGet();
  }

  private void givenCreateParameters() {
     createValue = AccountDAO
        .builder()
        .name("my-account")
        .balance(BigDecimal.valueOf(10000))
        .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
        .build();
  }

  private void givenGetParameters() {
    getValue = 1L;
  }

  private void givenServiceForCreate() {
    when(eventService.create(any(EventDAO.class))).thenReturn(
        Mono.just(EventDAO.builder()
                    .type(EventTypeEnum.ACCT_CREATION)
                    .accountId(1L)
                    .amount(BigDecimal.valueOf(1000))
                    .createdAt(OffsetDateTime.now(Clock.systemUTC()))
                    .build())
    );
    when(accountRepository.save(any(AccountDAO.class))).thenReturn(
        Mono.just(createValue)
    );
  }

  private void givenServiceForGet() {
    when(eventService.create(any(EventDAO.class))).thenReturn(
        Mono.just(EventDAO.builder()
                          .type(EventTypeEnum.ACCT_CREATION)
                          .accountId(1L)
                          .amount(BigDecimal.valueOf(1000))
                          .createdAt(OffsetDateTime.now(Clock.systemUTC()))
                          .build())
    );
    when(accountRepository.findById(any(Long.class))).thenReturn(
        Mono.just(expectedGetValue)
    );
  }

  private void givenExpectedCreateResponse() {
    expectedCreateValue = AccountDAO
        .builder()
        .name("my-account")
        .balance(BigDecimal.valueOf(10000))
        .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
        .build();
  }

  private void givenExpectedGetResponse() {
    expectedGetValue = AccountDAO
        .builder()
        .name("my-account")
        .balance(BigDecimal.valueOf(10000))
        .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
        .build();
  }

  private void whenExecuteCreate() {
    createResult = service.create(createValue);
  }

  private void whenExecuteGet() {
    getResult = service.get(getValue);
  }

  void thenIsOkCreate() {
    StepVerifier.create(createResult).expectNextMatches(e -> e.equals(expectedCreateValue)).verifyComplete();
  }

  void thenIsOkGet() {
    StepVerifier.create(getResult).expectNextMatches(e -> e.equals(expectedGetValue)).verifyComplete();
  }

}
