package com.xepelin.challenge.model.dao;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("accounts")
public class AccountDAO {

  @Id
  private Long id;
  private String name;
  private BigDecimal balance;
  @Column("created_at")
  private OffsetDateTime createdAt;

}
