
CREATE TABLE accounts
(
    id                  serial constraint account_pk primary key,
    name                varchar(50),
    balance             numeric,
    created_at          timestamp with time zone
);

CREATE TABLE events
(
    id                  serial constraint event_pk primary key,
    type                varchar(50),
    amount              numeric,
    account_id          bigint,
    created_at          timestamp with time zone
);

CREATE TABLE transactions
(
    id                 serial constraint transaction_pk primary key,
    account_id         bigint,
    type               varchar(50),
    amount             numeric,
    created_at         timestamp with time zone
);

