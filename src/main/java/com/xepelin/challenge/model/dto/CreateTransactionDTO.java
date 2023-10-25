package com.xepelin.challenge.model.dto;

import com.xepelin.challenge.model.TransactionTypeEnum;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransactionDTO {

  @NotNull
  private Long accountId;

  @NotNull
  private TransactionTypeEnum type;

  @NotNull(message = "minimal amount is 0.0")
  @DecimalMin(value = "0.0")
  private BigDecimal amount;

}
