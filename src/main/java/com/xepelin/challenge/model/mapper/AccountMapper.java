package com.xepelin.challenge.model.mapper;

import com.xepelin.challenge.model.dao.AccountDAO;
import com.xepelin.challenge.model.dto.AccountDTO;
import com.xepelin.challenge.model.dto.CreateAccountDTO;
import java.time.Clock;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  public AccountDTO toDTO(AccountDAO accountDAO) {
    return AccountDTO
        .builder()
        .id(accountDAO.getId())
        .name(accountDAO.getName())
        .balance(accountDAO.getBalance())
        .createdAt(accountDAO.getCreatedAt())
        .build();
  }

  public AccountDAO toDAO(CreateAccountDTO createAccountDTO) {
    return AccountDAO
        .builder()
        .name(createAccountDTO.getName())
        .balance(createAccountDTO.getInitialBalance())
        .createdAt(OffsetDateTime.now(Clock.systemUTC()))
        .build();
  }

}
