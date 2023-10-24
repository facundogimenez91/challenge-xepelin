package com.xepelin.challenge.model;

public enum EventTypeEnum {
  ACCT_CREATION,
  TRX_DEPOSIT,
  TRX_WITHDRAW;

  public static EventTypeEnum getByTrxType(TransactionTypeEnum transactionTypeEnum) {
    return switch (transactionTypeEnum) {
      case DEPOSIT -> TRX_DEPOSIT;
      case WITHDRAW -> TRX_WITHDRAW;
    };
  }

}
