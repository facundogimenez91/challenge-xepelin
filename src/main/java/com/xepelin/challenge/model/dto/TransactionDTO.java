package com.xepelin.challenge.model.dto;

import com.xepelin.challenge.core.HandlerResponse;
import com.xepelin.challenge.model.TransactionTypeEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO implements HandlerResponse {

  private Long id;

  private Long accountId;

  private TransactionTypeEnum type;

  private BigDecimal amount;

  private OffsetDateTime createdAt;

}
