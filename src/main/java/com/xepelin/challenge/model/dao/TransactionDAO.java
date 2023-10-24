package com.xepelin.challenge.model.dao;

import com.xepelin.challenge.model.TransactionTypeEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("transactions")
public class TransactionDAO {

  @Id
  private Long id;
  private Long accountId;
  private TransactionTypeEnum type;
  private BigDecimal amount;
  @Column("created_at")
  private OffsetDateTime createdAt;

}
