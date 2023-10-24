package com.xepelin.challenge.service;

import com.xepelin.challenge.model.EventTypeEnum;
import com.xepelin.challenge.model.dao.EventDAO;
import com.xepelin.challenge.repository.EventRepository;
import java.math.BigDecimal;
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
class EventServiceTest {

  @Mock
  private EventRepository eventRepository;
  private EventDAO createValue;
  private EventDAO expectedCreateValue;
  private Mono<EventDAO> createResult;
  @InjectMocks
  private EventServiceImpl service;


  @Test
  void givenRequest_whenExecuteCreate_thenIsOk() {
    givenCreateParameters();
    givenExpectedCreateResponse();
    givenServiceForCreate();
    whenExecuteCreate();
    thenIsOkCreate();
  }

  private void givenExpectedCreateResponse() {
    expectedCreateValue = EventDAO
        .builder()
        .accountId(1L)
        .amount(BigDecimal.valueOf(10000))
        .type(EventTypeEnum.ACCT_CREATION)
        .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
        .build();
  }

  private void givenCreateParameters() {
    createValue = EventDAO
        .builder()
        .accountId(1L)
        .amount(BigDecimal.valueOf(10000))
        .type(EventTypeEnum.ACCT_CREATION)
        .createdAt(OffsetDateTime.parse("2021-09-30T15:30:00+01:00"))
        .build();
  }

  private void givenServiceForCreate() {
    when(eventRepository.save(any(EventDAO.class))).thenReturn(
        Mono.just(createValue)
    );
  }

  private void whenExecuteCreate() {
    createResult = service.create(createValue);
  }

  private void thenIsOkCreate() {
    StepVerifier.create(createResult).expectNextMatches(e -> e.equals(expectedCreateValue)).verifyComplete();
  }

}
