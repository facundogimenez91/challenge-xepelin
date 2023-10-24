package com.xepelin.challenge.model.dao;

import com.xepelin.challenge.model.EventTypeEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("events")
public class EventDAO {

  @Id
  private Long id;
  @Column("account_id")
  private Long accountId;
  private EventTypeEnum type;
  private BigDecimal amount;
  @Column("created_at")
  private OffsetDateTime createdAt;

}
