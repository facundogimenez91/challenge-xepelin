package com.xepelin.challenge.model.dto;


import com.xepelin.challenge.core.HandlerResponse;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO implements HandlerResponse {

  private Long id;

  private String name;

  private BigDecimal balance;

  private OffsetDateTime createdAt;

}
