package com.xepelin.challenge.repository;

import com.xepelin.challenge.model.dao.TransactionDAO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<TransactionDAO, Long> {

}
