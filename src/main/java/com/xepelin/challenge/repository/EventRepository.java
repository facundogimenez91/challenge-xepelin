package com.xepelin.challenge.repository;

import com.xepelin.challenge.model.dao.EventDAO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ReactiveCrudRepository<EventDAO, Long> {

}
