package com.xepelin.challenge.model.dto;

import com.xepelin.challenge.model.TransactionTypeEnum;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransactionDTO {

  private Long accountId;

  private TransactionTypeEnum type;

  private BigDecimal amount;

}
