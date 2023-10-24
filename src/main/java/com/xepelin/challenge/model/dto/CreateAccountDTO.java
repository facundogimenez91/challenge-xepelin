package com.xepelin.challenge.model.dto;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateAccountDTO {

  @NotBlank(message = "name cannot be empty")
  private String name;
  @NotNull(message = "minimal initialBalance is 0.0")
  @DecimalMin(value = "0.0")
  private BigDecimal initialBalance;

}
