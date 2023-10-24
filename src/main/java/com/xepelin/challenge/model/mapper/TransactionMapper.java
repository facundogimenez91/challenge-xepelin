package com.xepelin.challenge.model.mapper;

import com.xepelin.challenge.model.dao.TransactionDAO;
import com.xepelin.challenge.model.dto.CreateTransactionDTO;
import com.xepelin.challenge.model.dto.TransactionDTO;
import java.time.Clock;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  public TransactionDTO toDTO(TransactionDAO transactionDAO) {
    return TransactionDTO
        .builder()
        .id(transactionDAO.getId())
        .type(transactionDAO.getType())
        .accountId(transactionDAO.getAccountId())
        .amount(transactionDAO.getAmount())
        .createdAt(transactionDAO.getCreatedAt())
        .build();
  }

  public TransactionDAO toDAO(CreateTransactionDTO createTransactionDTO) {
    return TransactionDAO
        .builder()
        .accountId(createTransactionDTO.getAccountId())
        .amount(createTransactionDTO.getAmount())
        .type(createTransactionDTO.getType())
        .createdAt(OffsetDateTime.now(Clock.systemUTC()))
        .build();
  }

}
