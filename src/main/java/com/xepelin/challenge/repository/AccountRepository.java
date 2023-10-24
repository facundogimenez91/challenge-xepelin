package com.xepelin.challenge.repository;

import com.xepelin.challenge.model.dao.AccountDAO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountDAO, Long> {

}
